package mm.pndaza.tipitakaabidan.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mm.pndaza.tipitakaabidan.Adapter.WordAdapter;
import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.database.DBOpenHelper;
import mm.pndaza.tipitakaabidan.model.Book;
import mm.pndaza.tipitakaabidan.model.Word;
import mm.pndaza.tipitakaabidan.utils.MDetect;

public class WordListActivity extends AppCompatActivity implements WordAdapter.OnItemClickListener {

    private Context context;
    private ArrayList<Word> words = new ArrayList<>();
    private WordAdapter adapter = null;
    private String bookname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);

        getSupportActionBar();
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        MDetect.init(context);
        Book book = getIntent().getParcelableExtra("book");
        bookname = book.getName();
        setTitle(MDetect.getDeviceEncodedText(bookname));

        RecyclerView recyclerView = findViewById(R.id.wordListView);
        adapter = new WordAdapter(words, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        new LoadWords().execute(book.getId());


    }

    public class LoadWords extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... books) {
            String book = books[0];
            int id;
            String word;
            Cursor cursor = DBOpenHelper.getInstance(context).getWords(book);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex("rowid"));
                    word = cursor.getString(cursor.getColumnIndex("word"));
                    words.add(new Word(id, word));
                } while (cursor.moveToNext());
            }
            cursor.close();

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(Word word) {

        DBOpenHelper.getInstance(this).getDetail(word);
        word.setBookName(bookname);
        Intent intent = new Intent(this, ReaderActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
