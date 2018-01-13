package com.tbg.pixtr.settings.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.tbg.pixtr.R;
import com.tbg.pixtr.settings.adapter.SettingsAdapter;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.tbg.pixtr.utils.misc.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements SettingsAdapter.OnClickListener {

    @BindView(R.id.settings)
    RecyclerView settings;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SettingsAdapter adapter;

    private AppUtils appUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setupView();
    }

    public void setupView() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        appUtils = new AppUtils();
        adapter = new SettingsAdapter();
        adapter.updateData(appUtils.getSettingsList());
        adapter.setOnClickListener(this);
        settings.setAdapter(adapter);
        settings.setLayoutManager(new LinearLayoutManager(this));
        settings.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void OnClick(int position) {
        if (position == 1) {
            Glide.get(this).clearDiskCache();
            Snackbar.make(settings, "Disk cache has been cleared", Snackbar.LENGTH_SHORT).show();
        } else if (position == 2) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://unsplash.com" + AppConstants.UTM_PARAMS));
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
