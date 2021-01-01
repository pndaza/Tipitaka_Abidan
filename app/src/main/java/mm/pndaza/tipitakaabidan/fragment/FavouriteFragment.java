package mm.pndaza.tipitakaabidan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mm.pndaza.tipitakaabidan.callback.SwipeToDeleteControler;
import mm.pndaza.tipitakaabidan.Adapter.FavouriteAdapter;
import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.database.DBOpenHelper;
import mm.pndaza.tipitakaabidan.model.Favourite;
import mm.pndaza.tipitakaabidan.utils.MDetect;
import mm.pndaza.tipitakaabidan.utils.Rabbit;


public class FavouriteFragment extends Fragment implements FavouriteAdapter.OnFavouriteItemClickListener {


    public interface OnFavouriteCallbackListener {
        void onFavouriteClick(Favourite favourite);
    }

    private OnFavouriteCallbackListener callbackListener;
    private ArrayList<Favourite> favourites = null;

    private FavouriteAdapter adapter;
    private TextView tv_empty_info;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.title_favourite_mm)));

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        favourites = DBOpenHelper.getInstance(getContext()).getAllFavourites();
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new FavouriteAdapter(favourites, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        tv_empty_info =  view.findViewById(R.id.empty_info);
        setupEmptyInfoView();

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteControler(context, adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callbackListener = (OnFavouriteCallbackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implemented OnWordlistSelectedListener");

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_clear_all, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_clear_all) {
            clearBookmarks();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearBookmarks() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context,R.style.AlertDialogTheme);

        String message = "သိမ်းမှတ်ထားသည်များကို ဖျက်မှာလား";
        String comfirm = "ဖျက်မယ်";
        String cancel = "မဖျက်တော့ဘူး";
        if (!MDetect.isUnicode()) {
            message = Rabbit.uni2zg(message);
            comfirm = Rabbit.uni2zg(comfirm);
            cancel = Rabbit.uni2zg(cancel);
        }

        alertDialog.setMessage(message)
                .setCancelable(true)
                .setPositiveButton(comfirm,
                        (dialog, id) -> {
                            adapter.deleteAll();
                            adapter.notifyDataSetChanged();
                            setupEmptyInfoView();
                        })
                .setNegativeButton(cancel, (dialog, id) -> {
                });
        alertDialog.show();
    }

    private void setupEmptyInfoView() {
        tv_empty_info.setText(MDetect.getDeviceEncodedText(getString(R.string.empty_favourite)));
        tv_empty_info.setVisibility(favourites.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void onFavouriteItemClick(Favourite favourite) {
        callbackListener.onFavouriteClick(favourite);
    }

}
