package com.tbg.pixtr.settings.views;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.tbg.pixtr.R;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.home.view.HomeActivity;
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
    private SharedPreferencesUtil preferencesUtil;

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
        preferencesUtil = new SharedPreferencesUtil(this);

        adapter.updateData(appUtils.getSettingsList(getBaseContext()));
        adapter.setOnClickListener(this);
        settings.setAdapter(adapter);
        settings.setLayoutManager(new LinearLayoutManager(this));
        settings.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void OnClick(int position) {
        if (position == 1) {
            AsyncTask.execute(() -> {
                Glide.get(SettingsActivity.this).clearDiskCache();
                Snackbar.make(settings, R.string.cache_cleared, Snackbar.LENGTH_SHORT).show();
            });
        } else if (position == 2) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://unsplash.com" + AppConstants.UTM_PARAMS));
            startActivity(intent);
        } else if (position == 4) {
            displayQualityDialog(AppConstants.QUALITY_FLAGS.LOAD);
        } else if (position == 5) {
            displayQualityDialog(AppConstants.QUALITY_FLAGS.DOWNLOAD);
        } else if (position == 6) {
            displayQualityDialog(AppConstants.QUALITY_FLAGS.WALLPAPER);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            onBackPressed();
        }
        return true;
    }


    /**
     * Dialog
     *
     * @param flags
     */
    public void displayQualityDialog(AppConstants.QUALITY_FLAGS flags) {
        int position = -1;
        String headerName = getString(R.string.quality);
        if (flags == AppConstants.QUALITY_FLAGS.WALLPAPER) {
            position = preferencesUtil.getWallpaperQuality();
            headerName = getString(R.string.wallpaper_quality);
        } else if (flags == AppConstants.QUALITY_FLAGS.LOAD) {
            position = preferencesUtil.getLoadQuality();
            headerName = getString(R.string.load_quality);
        } else if (flags == AppConstants.QUALITY_FLAGS.DOWNLOAD) {
            position = preferencesUtil.getDownloadQuality();
            headerName = getString(R.string.download_quality);
        }
        new MaterialDialog.Builder(this)
                .title(headerName)
                .items(R.array.quality_types)
                .itemsCallbackSingleChoice(position, (materialDialog, view, selectedPosition, charSequence) -> {
                    appUtils.editQualityData(selectedPosition, flags, preferencesUtil);
                    return true;
                })
                .show();


    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
