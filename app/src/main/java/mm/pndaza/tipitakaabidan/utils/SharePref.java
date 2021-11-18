package mm.pndaza.tipitakaabidan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import mm.pndaza.tipitakaabidan.data.Constants;

public class SharePref {

    private static final String PREF_iS_DATABASE_COPIED = "IsDatabaseCopied";
    private static final String PREF_DATABASE_VERSION = "DBVersion";
    private static final String PREF_NIGHT_MODE = "NightMode";
    private static final String PREF_SCROLL_MODE = "ScrollMode";


    private static final boolean DEFAULT_IS_DATABASE_COPIED = false;
    private static final int DEFAULT_DATABASE_VERSION = 1;
    private static final int DEFAULT_NIGHT_MODE = 0;
    private static final ScrollMode DEFAULT_SCROLL_MODE = ScrollMode.vertical;




    private Context context;
    private static SharePref prefInstance;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

    public SharePref(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharePref getInstance(Context Context) {
        if (prefInstance == null) {
            prefInstance = new SharePref(Context);
        }
        return prefInstance;
    }

    public boolean isDatabaseCopied() {
        return sharedPreferences.getBoolean(PREF_iS_DATABASE_COPIED, DEFAULT_IS_DATABASE_COPIED);
    }

    public void isDatabaseCopied(boolean value) {
        editor.putBoolean(PREF_iS_DATABASE_COPIED, value);
        editor.apply();
    }

    public int getDatabaseVersion() {
        return sharedPreferences.getInt(PREF_DATABASE_VERSION,
                DEFAULT_DATABASE_VERSION);
    }

    public void setDatabaseVersion(int version) {
        editor.putInt(PREF_DATABASE_VERSION, version);
        editor.apply();
    }

    public int getNightMode(){

        return sharedPreferences.getInt(PREF_NIGHT_MODE, DEFAULT_NIGHT_MODE);
    }

    public void setNightMode(int value){
        editor.putInt(PREF_NIGHT_MODE, value);
        editor.apply();
    }

    public ScrollMode getScrollMode() {
        String scrollModeName = sharedPreferences.getString(PREF_SCROLL_MODE,
                DEFAULT_SCROLL_MODE.name());
        return ScrollMode.toScrollMode(scrollModeName);
    }

    public void setScrollMode(ScrollMode scrollMode) {
        editor.putString(PREF_SCROLL_MODE, scrollMode.name());
        editor.apply();
    }


}
