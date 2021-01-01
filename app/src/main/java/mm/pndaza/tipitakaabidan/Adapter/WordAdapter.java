package mm.pndaza.tipitakaabidan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.model.Word;
import mm.pndaza.tipitakaabidan.utils.MDetect;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private ArrayList<Word> words;

    public WordAdapter(ArrayList<Word> words, OnItemClickListener onItemClickListener) {
        this.words = words;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(MDetect.getDeviceEncodedText(words.get(position).getWord()));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(words.get(getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Word word);
    }
}
