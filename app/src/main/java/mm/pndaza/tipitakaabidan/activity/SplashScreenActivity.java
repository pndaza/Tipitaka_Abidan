package mm.pndaza.tipitakaabidan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.utils.SharePref;


public class SplashScreenActivity extends AppCompatActivity {

    private static final String DATABASE_PATH = "databases";
    private static final String DATABASE_FILENAME = "tipidict.db";
    private String SAVED_PATH;
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        SAVED_PATH = getFilesDir().toString();
        context = this;

        // save default setting
        SharePref sharePref = SharePref.getInstance(this);
        if (sharePref.isFirstTime()) {
            sharePref.saveDefault();
        }

        // load theme
        if (SharePref.getInstance(this).getPrefNightModeState() == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }


        if (isDatabaseExist() && sharePref.isDatabaseCopied()) {
            startMainActivity();
        } else {
            setupDatabase();
        }

    }

    private boolean isDatabaseExist() {
        return new File(SAVED_PATH + "/" + DATABASE_PATH + "/" + DATABASE_FILENAME).exists();
    }

    private void setupDatabase() {

        new CopyDBAsync().execute();
    }

    public class CopyDBAsync extends AsyncTask<Void, Void, Void> {


        protected Void doInBackground(Void... voids) {

            File path = new File(SAVED_PATH + "/" + DATABASE_PATH );
            // check database folder is exist and if not, make folder.
            if (!path.exists()) {
                path.mkdirs();
            }

            try {
                InputStream inputStream = getAssets()
                        .open(DATABASE_PATH + "/" + DATABASE_FILENAME);
                OutputStream outputStream = new FileOutputStream(
                        SAVED_PATH + "/" + DATABASE_PATH + "/" + DATABASE_FILENAME);

                byte[] buffer = new byte[1024];
                int length;
                while (( length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                inputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            SharePref.getInstance(context).setDbCopyState(true);
            startMainActivity();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void startMainActivity() {

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            finish();
            SplashScreenActivity.this.startActivity(intent);
        }, 1000);

    }

}
