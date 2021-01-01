package mm.pndaza.tipitakaabidan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.database.DBOpenHelper;
import mm.pndaza.tipitakaabidan.model.Favourite;
import mm.pndaza.tipitakaabidan.utils.MDetect;
import mm.pndaza.tipitakaabidan.utils.SharePref;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Favourite> favourites;
    private OnFavouriteItemClickListener onFavouriteItemClickListener;
    private int fontSize = 18;

    public FavouriteAdapter(ArrayList<Favourite> favourites, OnFavouriteItemClickListener onFavouriteItemClickListener) {
        this.favourites = favourites;
        this.onFavouriteItemClickListener = onFavouriteItemClickListener;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View bookmark_row_item = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(bookmark_row_item);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder viewHolder, final int position) {
        final Favourite favourite = favourites.get(position);
        TextView tv_word = viewHolder.tv_word;
        tv_word.setText(MDetect.getDeviceEncodedText(favourite.getWord()));
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_word;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_word = itemView.findViewById(R.id.textview);
            tv_word.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFavouriteItemClickListener.onFavouriteItemClick(favourites.get(getAdapterPosition()));
        }
    }

    public void deleteItem(int position){

        DBOpenHelper.getInstance(context).removeFromFavourite(favourites.get(position).getId());
        favourites.remove(position);
        notifyDataSetChanged();
    }

    public void deleteAll(){
        DBOpenHelper.getInstance(context).removeAllFavourite();
        favourites.clear();
        notifyDataSetChanged();
    }

    public interface OnFavouriteItemClickListener {
        void onFavouriteItemClick(Favourite favourite);
    }

}
