package com.tbg.pixtr.settings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbg.pixtr.R;
import com.tbg.pixtr.settings.adapter.viewholder.SettingsHeaderViewHolder;
import com.tbg.pixtr.settings.adapter.viewholder.SettingsItemViewHolder;

/**
 * Created by kausthubhadhikari on 08/01/18.
 */

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int HEADER_VIEW_TYPE = 1;
    public static int ITEM_VIEW_TYPE = 2;
    public String[] title = {"Other", "Clear Cache", "Go to Unsplash.com", "Quality", "Load Quality", "Download Quality", "Wallpaper Quality"};
    public String[] description = {"Cache size: ", "Choose the quality of the images that are loaded.", "Choose the quality of the images that are downloaded.", "Choose the quality of the wallpaper"};
    public int[] itemViewType = {1, 2, 2, 1, 2, 2, 2};
    OnClickListener onClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_header_item_layout, parent, false);
            SettingsHeaderViewHolder headerViewHolder = new SettingsHeaderViewHolder(view);
            return headerViewHolder;
        } else if (viewType == ITEM_VIEW_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item_layout, parent, false);
            SettingsItemViewHolder itemViewHolder = new SettingsItemViewHolder(view);
            return itemViewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_header_item_layout, parent, false);
            SettingsHeaderViewHolder headerViewHolder = new SettingsHeaderViewHolder(view);
            return headerViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SettingsItemViewHolder) {
            ((SettingsItemViewHolder) holder).description.setText(description[position]);
            ((SettingsItemViewHolder) holder).title.setText(title[position]);
            holder.itemView.setOnClickListener(view -> onClickListener.OnClick(position));
        } else if (holder instanceof SettingsHeaderViewHolder) {
            ((SettingsHeaderViewHolder) holder).title.setText(title[position]);
        }
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    @Override
    public int getItemViewType(int position) {
        return itemViewType[position];
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        public void OnClick(int position);
    }
}
