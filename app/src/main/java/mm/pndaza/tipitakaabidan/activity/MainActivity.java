package mm.pndaza.tipitakaabidan.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.database.DBOpenHelper;
import mm.pndaza.tipitakaabidan.fragment.FavouriteFragment;
import mm.pndaza.tipitakaabidan.fragment.HomeFragment;
import mm.pndaza.tipitakaabidan.fragment.RecentFragment;
import mm.pndaza.tipitakaabidan.fragment.SearchFragment;
import mm.pndaza.tipitakaabidan.fragment.SettingFragment;
import mm.pndaza.tipitakaabidan.model.Book;
import mm.pndaza.tipitakaabidan.model.Favourite;
import mm.pndaza.tipitakaabidan.model.Recent;
import mm.pndaza.tipitakaabidan.model.Word;
import mm.pndaza.tipitakaabidan.utils.MDetect;
import mm.pndaza.tipitakaabidan.utils.SharePref;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnBookChoiceListener
        , SearchFragment.OnWordClickListener
        , FavouriteFragment.OnFavouriteCallbackListener
        , RecentFragment.OnRecentCallbackListener
        , SettingFragment.OnSettingChangeListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MDetect.init(this);

        getSupportActionBar();
        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle(MDetect.getDeviceEncodedText(getString(R.string.app_name)));

        // load theme
        if (SharePref.getInstance(this).getNightMode() == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

//        openFragment(new HomeFragment());
        if (savedInstanceState == null) {
            openFragment(new HomeFragment());
        }

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        navView.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFragment = null;
            switch ((item.getItemId())) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.navigation_favourite:
                    selectedFragment = new FavouriteFragment();
                    break;
                case R.id.navigation_recent:
                    selectedFragment = new RecentFragment();
                    break;
                case R.id.navigation_setting:
                    selectedFragment = new SettingFragment();
                    break;
            }
            openFragment(selectedFragment);
            return true;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void openFragment(Fragment selectedFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, selectedFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBookChoice(Book book) {
        Intent intent = new Intent(this, WordListActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }

    @Override
    public void onWordClick(Word word) {
        openBook(word);
    }

    @Override
    public void onFavouriteClick(Favourite favourite) {
        Word word = new Word(favourite.getId(), favourite.getWord());
        openBook(word);

    }

    @Override
    public void onRecentClick(Recent recent) {
        Word word = new Word(recent.getId(), recent.getWord());
        openBook(word);
    }

    @Override
    public void onChangeListener() {
        recreate();
    }

    private void openBook(Word word) {
        DBOpenHelper.getInstance(this).getDetail(word);
        word.setBookName(DBOpenHelper.getInstance(this).getBookName(word.getBookid()));
        Intent intent = new Intent(this, ReaderActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }
}
