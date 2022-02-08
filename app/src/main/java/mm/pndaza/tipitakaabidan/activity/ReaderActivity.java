package mm.pndaza.tipitakaabidan.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.database.DBOpenHelper;
import mm.pndaza.tipitakaabidan.model.Word;
import mm.pndaza.tipitakaabidan.utils.MDetect;
import mm.pndaza.tipitakaabidan.utils.ScrollMode;
import mm.pndaza.tipitakaabidan.utils.SharePref;

public class ReaderActivity extends AppCompatActivity {
    private static final String TAG = "ReaderActivity";
    private Word word;
    private PDFView pdfView;
//    private String assetName;
    private String pdfFileName;
    private SharePref sharePref;
    private ScrollMode scrollMode;
    private boolean nightMode;
    private int currentPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        getSupportActionBar();
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MDetect.init(this);

        //from other app request
        if(getIntent().hasExtra("lookup_word")){
            String lookupWord= getIntent().getStringExtra("lookup_word");
            word = DBOpenHelper.getInstance(this).getWordInfo(lookupWord);
        } else {
            word = getIntent().getParcelableExtra("word");
        }

        setTitle(MDetect.getDeviceEncodedText(word.getBookName()));
        pdfFileName = word.getBookid() + ".pdf";

        manageRecent(word.getId());

        currentPage = word.getPageNumber() - 1;

        sharePref = SharePref.getInstance(this);
        scrollMode = sharePref.getScrollMode();
        nightMode = false;
        if(sharePref.getNightMode() == 2){
            nightMode = true;
        }

        pdfView = findViewById(R.id.pdfView);

        loadPdf();


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_reader, menu);
        MenuItem fav = menu.findItem(R.id.menu_favourite);
        setIcon(fav); // set icon based on bookmark exist ot not
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favourite:
                manageFavourites(item);
                return true;
            case R.id.menu_scroll_mode:
                swapIcon(item);
                changePageMode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void manageFavourites(MenuItem item){
        if(isFavouriteExist(word.getId())){
            removeFromFavourite(word.getId());
            item.setIcon(R.drawable.ic_add_to_favourite);
        } else {
            addToFavourite(word.getId());
            item.setIcon(R.drawable.ic_added_favorite);
        }
    }

    private boolean isFavouriteExist(int id) {
        return DBOpenHelper.getInstance(this).isFavouriteExist(id);
    }

    private void addToFavourite(int id) {
        DBOpenHelper.getInstance(this).addToFavourite(id);
        Snackbar.make(pdfView, MDetect.getDeviceEncodedText("စိတ်ကြိုက်စာရင်းသို့ ထည့်လိုက်ပါပြီ။"), Snackbar.LENGTH_SHORT).show();
    }

    private void removeFromFavourite(int id) {
        DBOpenHelper.getInstance(this).removeFromFavourite(id);
        Snackbar.make(pdfView, MDetect.getDeviceEncodedText("စိတ်ကြိုက်စာရင်းမှ ပယ်ဖျက်လိုက်ပါပြီ"), Snackbar.LENGTH_SHORT).show();
    }

    private void setIcon(MenuItem item) {
        item.setIcon(isFavouriteExist(word.getId()) ? R.drawable.ic_added_favorite : R.drawable.ic_add_to_favourite);
    }

    private void manageRecent(int id){
        if(!isRecentExist(id)){
            addToRecent(id);
        }
    }

    private boolean isRecentExist(int id){
        return DBOpenHelper.getInstance(this).isRecentExist(id);
    }

    private void addToRecent(int id) {
        DBOpenHelper.getInstance(this).addToRecent(id);
    }

    private void swapIcon(MenuItem item) {
        if (scrollMode == ScrollMode.vertical) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_swap_horiz_24));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_swap_vert_24));
        }

    }

    private void changePageMode() {

        currentPage = pdfView.getCurrentPage();
        if (scrollMode == ScrollMode.vertical) {
            scrollMode = ScrollMode.horizontal;
        } else {
            scrollMode = ScrollMode.vertical;
        }
        sharePref.setScrollMode(scrollMode);

        loadPdf();

    }

    private void loadPdf() {
        try {
            Context context = createPackageContext("mm.pndaza.tipitakaabidan", 0);
            AssetManager assetManager = context.getAssets();

            InputStream inputStream = assetManager.open("books" + File.separator
                    + pdfFileName);

            pdfView.fromStream(inputStream)
                    .defaultPage(currentPage)
                    .enableSwipe(true)
                    .pageFitPolicy(scrollMode == ScrollMode.horizontal
                            ? FitPolicy.BOTH : FitPolicy.WIDTH)
                    .swipeHorizontal(scrollMode == ScrollMode.horizontal)
                    .pageSnap(scrollMode == ScrollMode.horizontal)
                    .autoSpacing(scrollMode == ScrollMode.horizontal)
                    .pageFling(scrollMode == ScrollMode.horizontal)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .nightMode(nightMode)
                    .load();


        } catch (PackageManager.NameNotFoundException | IOException e) {
            e.printStackTrace();
        }

//        pdfView.fromAsset(assetName)
//                .defaultPage(currentPage)
//                .enableSwipe(true)
//                .pageFitPolicy(scrollMode == ScrollMode.horizontal ? FitPolicy.BOTH : FitPolicy.WIDTH)
//                .swipeHorizontal(scrollMode == ScrollMode.horizontal)
//                .pageSnap(scrollMode == ScrollMode.horizontal)
//                .autoSpacing(scrollMode == ScrollMode.horizontal)
//                .pageFling(scrollMode == ScrollMode.horizontal)
//                .scrollHandle(new DefaultScrollHandle(this))
//                .nightMode(nightMode)
//                .load();
    }

}
