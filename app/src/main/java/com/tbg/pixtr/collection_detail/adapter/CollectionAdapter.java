package com.tbg.pixtr.collection_detail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tbg.pixtr.R;
import com.tbg.pixtr.collection_detail.adapter.viewholder.CollectionViewholder;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kausthubhadhikari on 27/12/17.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionViewholder> {

    private Context context;
    private ArrayList<CollectionDetailsPojo> data = new ArrayList<>();

    public CollectionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CollectionViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item_viewholder, parent, false);
        CollectionViewholder viewholder = new CollectionViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(CollectionViewholder holder, int position) {
        Glide.with(context)
                .load(data.get(position).urls.regular)
                .into(holder.imagePreview);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<CollectionDetailsPojo> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

}
