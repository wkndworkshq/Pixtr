package com.tbg.pixtr.collection_detail.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tbg.pixtr.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kausthubhadhikari on 27/12/17.
 */

public class CollectionViewholder extends RecyclerView.ViewHolder {

    @BindView(R.id.imagePreview)
    public ImageView imagePreview;

    @BindView(R.id.imageScrim)
    View imageScrim;

    public CollectionViewholder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setOverlayColor(int color) {
        imageScrim.setBackgroundColor(color);
    }
}
