package mm.pndaza.tipitakaabidan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.model.Book;
import mm.pndaza.tipitakaabidan.utils.MDetect;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private ArrayList<Book> books;

    public BookAdapter(ArrayList<Book> books, OnItemClickListener onItemClickListener) {
        this.books = books;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(MDetect.getDeviceEncodedText(books.get(position).getName()));
        holder.tv_name_info.setText(MDetect.getDeviceEncodedText(books.get(position).getNameInfo()));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_name;
        TextView tv_name_info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_name_info = itemView.findViewById(R.id.tv_name_info);
            tv_name.setOnClickListener(this);
            tv_name_info.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(books.get(getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
}
