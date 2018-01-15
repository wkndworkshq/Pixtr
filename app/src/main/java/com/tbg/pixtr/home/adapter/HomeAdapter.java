package com.tbg.pixtr.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.tbg.pixtr.R;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.home.adapter.viewholder.HeadViewHolder;
import com.tbg.pixtr.home.adapter.viewholder.ItemViewHolder;
import com.tbg.pixtr.model.pojo.collections.CollectionsPojo;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.tbg.pixtr.utils.misc.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kausthubhadhikari on 26/12/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CollectionsPojo> collections = new ArrayList<>();
    private Context context;
    private ActivityInteractions interactions;
    private SharedPreferencesUtil preferencesUtil;
    private AppUtils appUtils;

    public HomeAdapter(Context context, SharedPreferencesUtil preferencesUtil, AppUtils appUtils) {
        this.context = context;
        this.preferencesUtil = preferencesUtil;
        this.appUtils = appUtils;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AppConstants.HEADER_ITEM_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item_layout, parent, false);
            HeadViewHolder viewHolder = new HeadViewHolder(view);
            return viewHolder;
        } else if (viewType == AppConstants.ITEM_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_viewholder, parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item_layout, parent, false);
            HeadViewHolder viewHolder = new HeadViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(new ColorDrawable(Color.parseColor("#424242")));

            DrawableTransitionOptions drawableTransitionOptions = new DrawableTransitionOptions();
            drawableTransitionOptions.crossFade();

            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(appUtils.retrieveLoadURLConfig(collections.get(position).coverPhoto.urls, preferencesUtil, AppConstants.QUALITY_FLAGS.LOAD))
                    .transition(drawableTransitionOptions)
                    .into(itemViewHolder.collectionImage);

            itemViewHolder.collectionTitle.setText(collections.get(position).title);
            itemViewHolder.itemView.setOnClickListener(view -> interactions.onClick(position));
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    public void updateData(List<CollectionsPojo> data) {
        collections.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return collections.get(position).type;
    }


    /**
     * Setup the interface.
     *
     * @param activityInteraction
     */
    public void setOnActivityInteraction(ActivityInteractions activityInteraction) {
        this.interactions = activityInteraction;
    }


    /**
     * Interface to connect with the activity.
     */
    public interface ActivityInteractions {
        void onClick(int position);
    }

    public int getRandom() {
        Random random = new Random();
        return random.nextInt((3 - 0) + 1) + 0;
    }

    public String getId(int position) {
        return "" + collections.get(position).id;
    }

    public CollectionsPojo getData(int position) {
        return collections.get(position);
    }

}
