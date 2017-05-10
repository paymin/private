package id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by paymin on 10/05/2017.
 */

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    private List<BannerItem> bannerItemList;
    private Context context;

    public BannerAdapter(List<BannerItem> bannerItemList, Context context) {
        this.bannerItemList = bannerItemList;
        this.context = context;
    }

    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BannerAdapter.ViewHolder holder, int position) {
        final BannerItem bannerItem = bannerItemList.get(position);

        Glide
                .with(context)
                .load(bannerItem.getImageUrl())
                .into(holder.ivBanner);

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView ivBanner;

        public ViewHolder(View itemView) {
            super(itemView);
            ivBanner = (ImageView) itemView.findViewById(R.id.imageViewBanner);

            //textViewHead.setText();
        }
    }
}
