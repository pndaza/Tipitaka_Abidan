package mm.pndaza.tipitakaabidan.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import mm.pndaza.tipitakaabidan.data.Constants;
import mm.pndaza.tipitakaabidan.model.Book;
import mm.pndaza.tipitakaabidan.model.Favourite;
import mm.pndaza.tipitakaabidan.model.Recent;
import mm.pndaza.tipitakaabidan.model.Word;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static DBOpenHelper sInstance;
//    private static final String DATABASE_PATH = "/databases/";
//    private static final String DATABASE_NAME = "tipidict.db";
//    private static final int DATABASE_VERSION = 1;

    private static final String TAG = "DBOpenHelper";


    public static synchronized DBOpenHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DBOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBOpenHelper(Context context) {
        super(context, context.getFilesDir() + "/databases/"
                + Constants.DATABASE_FILE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String getBookName(String bookid){
        String bookName = "";
        Cursor cursor = getReadableDatabase().rawQuery("SELECT name FROM books WHERE id = '"
                + bookid + "'", null);
        if (cursor != null && cursor.moveToFirst()) {
            bookName = cursor.getString(cursor.getColumnIndex("name"));
        }
        return bookName;
    }

    public ArrayList<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>();
        String id;
        String name;
        String nameInfo;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT id, name, name_info FROM books", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndex("id"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                nameInfo = cursor.getString(cursor.getColumnIndex("name_info"));
                books.add(new Book(id, name, nameInfo));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return books;
    }


    public Cursor getWords(String bookid) {
        return getReadableDatabase().rawQuery(
                "SELECT rowid, word FROM words WHERE bookid = '" + bookid + "' ORDER BY rowid ASC", null);
    }

    public void getDetail(Word word) {


        String bookid = "1";
        int pageNumber = 1;
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT bookid, page FROM words WHERE rowid = " + word.getId(), null);
        if (cursor != null && cursor.moveToFirst()) {
            bookid = cursor.getString(cursor.getColumnIndex("bookid"));
            pageNumber = cursor.getInt(cursor.getColumnIndex("page"));
        }
        cursor.close();

        cursor = getReadableDatabase().rawQuery(
                "SELECT start_page FROM books Where id = '" + bookid + "'", null);
        if (cursor != null && cursor.moveToFirst()) {
            pageNumber += cursor.getInt(cursor.getColumnIndex("start_page"));
        }


        word.setBookid(bookid);
        word.setPageNumber(pageNumber);

    }

    public String getWord(int id) {
        String word = "";
        Cursor cursor = getReadableDatabase().rawQuery("SELECT word FROM words WHERE rowid = " + id, null);
        if (cursor != null && cursor.moveToFirst()) {
            word = cursor.getString(cursor.getColumnIndex("word"));
        }
        cursor.close();
        return word;
    }

    public Word getWordInfo(String lookup ) {
        Word word = null;
        int id;
        String str_word;
        String bookid = null;
        int pageNumber = 0;
        Cursor cursor = getReadableDatabase().rawQuery
                ("SELECT rowid,word, bookid,page FROM words WHERE word = '" + lookup + "'", null);
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("rowid"));
            str_word = cursor.getString(cursor.getColumnIndex("word"));
            bookid = cursor.getString(cursor.getColumnIndex("bookid"));
            pageNumber = cursor.getInt(cursor.getColumnIndex("page"));

            word = new Word(id,str_word);
            word.setBookid(bookid);
            word.setBookName(getBookName(bookid));
        }
        cursor.close();

        cursor = getReadableDatabase().rawQuery(
                "SELECT start_page FROM books Where id = '" + bookid + "'", null);
        if (cursor != null && cursor.moveToFirst()) {
            pageNumber += cursor.getInt(cursor.getColumnIndex("start_page"));
        }
        cursor.close();
        word.setPageNumber(pageNumber);
        return word;
    }

    public ArrayList<Favourite> getAllFavourites() {
        ArrayList<Favourite> favourites = new ArrayList<>();
        int id;
        String word;
        Cursor cursor = getReadableDatabase().
                rawQuery("SELECT wordid FROM favourite ORDER BY rowid DESC", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("wordid"));
                    word = getWord(id);
                    favourites.add(new Favourite(id, word));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return favourites;
    }

    public boolean isFavouriteExist(int id) {
        Cursor cursor = this.getReadableDatabase().rawQuery
                ("SELECT wordid FROM favourite Where wordid = " + id, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void addToFavourite(int id) {
        getWritableDatabase()
                .execSQL("INSERT INTO favourite (wordid) VALUES (?)", new Object[]{id});
    }

    public void removeFromFavourite(int id) {
        getWritableDatabase()
                .execSQL("DELETE FROM favourite WHERE wordid = ?", new Object[]{id});
    }

    public void removeAllFavourite() {
        getWritableDatabase()
                .execSQL("DELETE FROM favourite");
    }

    public ArrayList<Recent> getAllRecents() {
        ArrayList<Recent> recents = new ArrayList<>();
        int id;
        String word;
        Cursor cursor = getReadableDatabase().
                rawQuery("SELECT wordid FROM recent ORDER BY rowid DESC", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("wordid"));
                    word = getWord(id);
                    recents.add(new Recent(id, word));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return recents;
    }

    public boolean isRecentExist(int id) {
        Cursor cursor = this.getReadableDatabase().rawQuery
                ("SELECT wordid FROM recent Where wordid = " + id, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void addToRecent(int id) {
        getWritableDatabase()
                .execSQL("INSERT INTO recent (wordid) VALUES (?)", new Object[]{id});
    }

/*
    public void removeFromRecent(int id) {
        getWritableDatabase()
                .execSQL("DELETE FROM recent WHERE id = ?", new Object[]{id});
    }
*/

    public void removeAllRecent(){
        getWritableDatabase().execSQL("DELETE FROM recent");
    }

}
