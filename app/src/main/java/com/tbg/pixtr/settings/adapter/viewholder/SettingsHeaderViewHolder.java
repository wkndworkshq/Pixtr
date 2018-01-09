package com.tbg.pixtr.settings.adapter.viewholder;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tbg.pixtr.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kausthubhadhikari on 08/01/18.
 */

public class SettingsHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_title)
    public AppCompatTextView title;

    public SettingsHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
