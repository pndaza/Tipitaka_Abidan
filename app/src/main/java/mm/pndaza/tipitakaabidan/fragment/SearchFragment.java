package mm.pndaza.tipitakaabidan.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mm.pndaza.tipitakaabidan.Adapter.WordAdapter;
import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.database.DBOpenHelper;
import mm.pndaza.tipitakaabidan.model.Word;
import mm.pndaza.tipitakaabidan.utils.MDetect;
import mm.pndaza.tipitakaabidan.utils.PaliTextNormalizer;
import mm.pndaza.tipitakaabidan.utils.Rabbit;

public class SearchFragment extends Fragment implements WordAdapter.OnItemClickListener {

    public interface OnWordClickListener {
        void onWordClick(Word word);
    }

    private Context context;
    private ArrayList<Word> words = new ArrayList<>();
    private WordAdapter adapter;
    private RecyclerView recyclerView;
    private RadioGroup radioGroup_SearchOptions;
    private OnWordClickListener onWordClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = getContext();
        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.title_search_mm)));
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListView(view);

        final EditText searchInput = view.findViewById(R.id.search);
        final ImageButton btnClear = view.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(v -> searchInput.setText(""));
//        setupClearButton(btnClear, searchInput);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                doSearch(editable.toString());
            }
        });

        searchInput.setFocusable(true);
        searchInput.setHint(MDetect.getDeviceEncodedText(getString(R.string.search_hint)));
        searchInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchInput, InputMethodManager.SHOW_IMPLICIT);
//        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onWordClickListener = (OnWordClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implemented OnWordClickListener");

        }
    }

    private void initListView(View view) {

        radioGroup_SearchOptions = view.findViewById(R.id.search_options);
        RadioButton rb_start_with = view.findViewById(R.id.radio_btn_start_with);
        RadioButton rb_anywhere = view.findViewById(R.id.radio_btn_anywhere);

        String start_with = getString(R.string.start_with);
        String anywhere = getString(R.string.anywhere);

        if (!MDetect.isUnicode()) {
            rb_start_with.setText(Rabbit.uni2zg(start_with));
            rb_anywhere.setText(Rabbit.uni2zg(anywhere));
        }

        adapter = new WordAdapter(words, this);
        recyclerView = view.findViewById(R.id.wordListView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
    }

    private void doSearch(String query) {


        if (query.length() == 0) {
            words.clear();
            adapter.notifyDataSetChanged();
        } else if (isValid(query)) {
            if (query.length() == 1) {
                MatchSearch(query);
            } else {
                LikeSearch(query);
            }
        }
    }

    private void MatchSearch(String query) {
        SQLiteDatabase sqLiteDatabase = DBOpenHelper.getInstance(context).getReadableDatabase();
        int id;
        String word;
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT rowid, word FROM words WHERE word = '" + query + "' ORDER BY word ASC", null);
        if (cursor != null && cursor.moveToFirst()) {
            words.clear();
            do {
                id = cursor.getInt(cursor.getColumnIndex("rowid"));
                word = cursor.getString(cursor.getColumnIndex("word"));
                words.add(new Word(id, word));
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();
        } else {
            words.clear();
            adapter.notifyDataSetChanged();
        }
        cursor.close();
        sqLiteDatabase.close();
    }

    @SuppressLint("StaticFieldLeak")
    private void LikeSearch(String query) {

        int idOfSearchOption = radioGroup_SearchOptions.getCheckedRadioButtonId();


        new AsyncTask<String, Integer, Void>() {
            int previousFound = 0;

            @Override
            protected Void doInBackground(String... querys) {

                SQLiteDatabase sqLiteDatabase = DBOpenHelper.getInstance(context).getReadableDatabase();
                int id;
                String word;
                String normalizedQuery = PaliTextNormalizer.normalize(querys[0]);
                String whereClause = "WHERE word LIKE '" + normalizedQuery + "%' ORDER BY rowid ASC";
                if (idOfSearchOption == R.id.radio_btn_anywhere) {
                    whereClause = "WHERE word LIKE '%" + normalizedQuery + "%'";
                }
                Cursor cursor = sqLiteDatabase.rawQuery(
                        "SELECT rowid, word FROM words " + whereClause, null);
                if (cursor != null && cursor.moveToFirst()) {
                    words.clear();
                    do {
                        id = cursor.getInt(cursor.getColumnIndex("rowid"));
                        word = cursor.getString(cursor.getColumnIndex("word"));
                        words.add(new Word(id, word));
                        publishProgress(words.size());
                    } while (cursor.moveToNext());
                } else {
                    words.clear();
                }
                cursor.close();
                sqLiteDatabase.close();

                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... progress) {
                super.onProgressUpdate(progress);

                if (previousFound + 10 > progress[0]) {
                    previousFound = progress[0];
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute(query);


    }

    private boolean isValid(String query) {
        if (query.contains("[^\u1000-\u109f")) {
            return false;
        } else if (query.startsWith("\u1031") || query.endsWith("\u1031")) {
            if (!MDetect.isUnicode()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onItemClick(Word word) {
        onWordClickListener.onWordClick(word);

    }


}
