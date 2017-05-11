package id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paymin on 11/05/2017.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private final Context context;
    ArrayList<FavouriteItem> fItem;

    public FavouriteAdapter(ArrayList<FavouriteItem> favouriteItem, Context context){
        this.fItem = favouriteItem;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FavouriteItem favouriteItem = fItem.get(position);
        holder.textViewHeadfav.setText(favouriteItem.judul);
        holder.textViewDescfav.setText(favouriteItem.deskripsi);
        Glide
                .with(context)
                .load(favouriteItem.urlgambar)
                .into(holder.imageViewOtoffav);
    }

    @Override
    public int getItemCount() {
        return fItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHeadfav;
        public TextView textViewDescfav;
        public ImageView imageViewOtoffav;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHeadfav = (TextView) itemView.findViewById(R.id.textViewHeadfav);
            textViewDescfav = (TextView) itemView.findViewById(R.id.textViewDescfav);
            imageViewOtoffav = (ImageView) itemView.findViewById(R.id.imageViewOtoffav);

        }
    }

}
