package com.tbg.pixtr.settings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbg.pixtr.R;
import com.tbg.pixtr.model.pojo.settings.SettingsPojo;
import com.tbg.pixtr.settings.adapter.viewholder.SettingsHeaderViewHolder;
import com.tbg.pixtr.settings.adapter.viewholder.SettingsItemViewHolder;

import java.util.ArrayList;

/**
 * Created by kausthubhadhikari on 08/01/18.
 */

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int HEADER_VIEW_TYPE = 1;
    public static int ITEM_VIEW_TYPE = 2;
    OnClickListener onClickListener;
    ArrayList<SettingsPojo> settings = new ArrayList<>();

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
            ((SettingsItemViewHolder) holder).description.setText(settings.get(position).descriptionName);
            ((SettingsItemViewHolder) holder).title.setText(settings.get(position).headerName);
            holder.itemView.setOnClickListener(view -> onClickListener.OnClick(position));
        } else if (holder instanceof SettingsHeaderViewHolder) {
            ((SettingsHeaderViewHolder) holder).title.setText(settings.get(position).headerName);
        }
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    @Override
    public int getItemViewType(int position) {
        return settings.get(position).type;
    }

    public void updateData(ArrayList<SettingsPojo> data){
        this.settings = data;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void OnClick(int position);
    }
}
