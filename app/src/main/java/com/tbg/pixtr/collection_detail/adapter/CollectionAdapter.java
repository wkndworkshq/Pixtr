package com.tbg.pixtr.collection_detail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.tbg.pixtr.R;
import com.tbg.pixtr.collection_detail.adapter.viewholder.CollectionViewholder;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.tbg.pixtr.utils.misc.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kausthubhadhikari on 27/12/17.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionViewholder> {

    private Context context;
    private ArrayList<CollectionDetailsPojo> data = new ArrayList<>();
    private OnClickListener clickListener;
    private AppUtils appUtils;
    private SharedPreferencesUtil preferencesUtil;

    public CollectionAdapter(Context context, SharedPreferencesUtil preferencesUtil, AppUtils appUtils) {
        this.context = context;
        this.preferencesUtil = preferencesUtil;
        this.appUtils = appUtils;
    }

    @Override
    public CollectionViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item_viewholder, parent, false);
        CollectionViewholder viewholder = new CollectionViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(CollectionViewholder holder, int position) {
        DrawableTransitionOptions drawableTransitionOptions = new DrawableTransitionOptions();
        drawableTransitionOptions.crossFade();

        Glide.with(context)
                .load(appUtils.retrieveLoadURLConfig(data.get(position).urls, preferencesUtil, AppConstants.QUALITY_FLAGS.LOAD))
                .transition(drawableTransitionOptions)
                .into(holder.imagePreview);

        holder.divider.setVisibility(position == data.size() - 1 ? View.GONE : View.VISIBLE);
        holder.itemView.setOnClickListener(view -> clickListener.onClick(position));
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.clickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<CollectionDetailsPojo> data) {
        if (this.data.containsAll(data)) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public CollectionDetailsPojo getData(int position) {
        return data.get(position);
    }

}
