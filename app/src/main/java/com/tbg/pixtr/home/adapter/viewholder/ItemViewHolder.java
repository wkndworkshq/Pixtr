package com.tbg.pixtr.home.adapter.viewholder;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tbg.pixtr.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kausthubhadhikari on 26/12/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.collectionImage)
    public ImageView collectionImage;

    @BindView(R.id.collectionTitle)
    public AppCompatTextView collectionTitle;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
