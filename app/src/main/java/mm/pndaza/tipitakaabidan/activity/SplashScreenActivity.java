package mm.pndaza.tipitakaabidan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.data.Constants;
import mm.pndaza.tipitakaabidan.utils.SharePref;


public class SplashScreenActivity extends AppCompatActivity {


    //    private static final String DATABASE_PATH = "databases";
//    private static final String DATABASE_FILENAME = "tipitaka_abidan.db";
    private String SAVED_PATH;
//    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // call AppCompatDelegate's setDefaultNightMode method before super.onCrete
        // otherwise it will trow error on some phone
        // E/ActivityInjector: get life cycle exception
        // java.lang.ClassCastException: android.os.BinderProxy cannot be cast to
        // android.app.servertransaction.ClientTransaction

        SharePref sharePref = SharePref.getInstance(this);
        if (sharePref.getNightMode() == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        SAVED_PATH = getFilesDir().toString()  + File.separator + Constants.DATABASE_PATH ;
//        context = this;

        File databaseFile = new File(SAVED_PATH , Constants.DATABASE_FILE_NAME);

        boolean isDatabaseCopied = sharePref.isDatabaseCopied();
        boolean isDatabaseFileExist = databaseFile.exists();
        int savedDatabaseVersion = sharePref.getDatabaseVersion();
        int latestDatabaseVersion = Constants.DATABASE_VERSION;

        if (isDatabaseCopied && isDatabaseFileExist) {
            Log.d("onCreate", "database exist");
            if(savedDatabaseVersion == latestDatabaseVersion){
                startMainActivity();
            } else {
                Log.d("db setup mode", "updating");
                updateDatabase();
            }
        } else {
            Log.d("db setup mode", "first time");
            setupDatabase();
        }

    }

    private void updateDatabase(){
        deleteDatabase();
        setupDatabase();
    }

    private void deleteDatabase() {
        // deleting  temporary files created by sqlite
        File temp1 = new File(SAVED_PATH, Constants.DATABASE_FILE_NAME + "-shm");
        if (temp1.exists()) {
            temp1.delete();
        }
        File temp2 = new File(SAVED_PATH, Constants.DATABASE_FILE_NAME  + "-wal");
        if (temp2.exists()) {
            temp2.delete();
        }

        new File(SAVED_PATH, Constants.DATABASE_FILE_NAME ).delete();
    }


    private void setupDatabase() {

        File file = new File(SAVED_PATH , Constants.DATABASE_FILE_NAME);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            // check databases folder is exist and if not, make folder.
            if (!file.getParentFile().exists()) {
                final boolean result = file.getParentFile().mkdirs();
                Log.d("folder creation result", String.valueOf(result));
            }

            try {
                InputStream input = SplashScreenActivity.this.getAssets().open(
                        Constants.DATABASE_PATH + File.separator
                                + Constants.DATABASE_FILE_NAME);
                OutputStream output = new FileOutputStream(file);


                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
                input.close();
                output.close();

                Log.i("db copy", "success");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }

            handler.post(() -> {
                //UI Thread work here
                SharePref sharePref = SharePref.getInstance(this);
                sharePref.isDatabaseCopied(true);
                sharePref.setDatabaseVersion(Constants.DATABASE_VERSION);
                startMainActivity();
            });
        });

    }

    private void startMainActivity() {

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            SplashScreenActivity.this.startActivity(intent);
            SplashScreenActivity.this.finish();
        }, 500);

    }

}
