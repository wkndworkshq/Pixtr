package com.tbg.pixtr.detail.view;

import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.tbg.pixtr.R;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.detail.presenter.DetailPresenter;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.base.BaseActivity;
import com.tbg.pixtr.utils.custom.CustomFAB;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.tbg.pixtr.utils.misc.AppUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailView {

    @Inject
    DetailPresenter presenter;

    @Inject
    AppUtils appUtils;

    @Inject
    SharedPreferencesUtil preferencesUtil;

    private CollectionDetailsPojo data;

    @BindView(R.id.locationLbl)
    AppCompatTextView locationLbl;

    @BindView(R.id.artistLbl)
    AppCompatTextView artistLbl;

    @BindView(R.id.likeLbl)
    AppCompatTextView likeLbl;

    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.fab)
    CustomFAB customFAB;

    @BindView(R.id.fab_sheet)
    View fab_sheet;

    @BindView(R.id.overlay)
    View overlay;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tintScrim)
    View tintScrim;

    MaterialSheetFab materialSheetFab;

    private int page = 1;

    private long downloadReferenceId;

    private DownloadManager downloadManager;

    private IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_new);
        ButterKnife.bind(this);
        data = new Gson().fromJson(getIntent().getStringExtra(AppConstants.INTENT_DETAILS_DATA), CollectionDetailsPojo.class);
        super.setStatusFlags(statusFlags.TransparentStatusBar);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void inject(Injector injector) {
        injector.inject(this);
    }

    @Override
    public DetailPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onNetworkError(Throwable throwable) {

    }

    @Override
    public void setupView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showProgress();
        getWindow().setStatusBarColor(Color.parseColor("#80000000"));
        materialSheetFab = new MaterialSheetFab<>(customFAB, fab_sheet, overlay, ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.colorAccent));
        locationLbl.setText(data.user.location == null ? "Not Available" : data.user.location);
        artistLbl.setText("By " + data.user.name);
        likeLbl.setText(data.likes + " Likes");

        DrawableTransitionOptions drawableTransitionOptions = new DrawableTransitionOptions();
        drawableTransitionOptions.crossFade();

        Glide.with(this)
                .load(data.urls.full)
                .transition(drawableTransitionOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        hideProgress();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        hideProgress();
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        tintScrim.setVisibility(View.VISIBLE);
        customFAB.hide();
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        tintScrim.setVisibility(View.GONE);
        customFAB.show();
    }

    @OnClick({R.id.downWallpaperImage, R.id.downWallpaperLbl, R.id.setWallpaperImage, R.id.setWallpaperLbl, R.id.viewWebsiteImage, R.id.viewWebsiteLbl})
    public void onClick(View view) {
        if (view.getId() == R.id.downWallpaperLbl || view.getId() == R.id.downWallpaperImage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_DOWNLOAD_PERMISSION);
            } else {
                downloadData(appUtils.retrieveLoadURLConfig(data.urls, preferencesUtil, AppConstants.QUALITY_FLAGS.DOWNLOAD));
            }
            presenter.updateDownloadStart(data.id);
        } else if (view.getId() == R.id.setWallpaperLbl || view.getId() == R.id.setWallpaperImage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_SET_WALLPAPAER_PERMISSION);
            } else {
                setWallpaperLogic();
            }
            presenter.updateDownloadStart(data.id);
        } else if (view.getId() == R.id.viewWebsiteLbl || view.getId() == R.id.viewWebsiteImage) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.links.html + AppConstants.UTM_PARAMS));
            startActivity(intent);
        }
        materialSheetFab.hideSheet();
    }


    /**
     * Request permission handler method,
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstants.REQUEST_DOWNLOAD_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadData(appUtils.retrieveLoadURLConfig(data.urls, preferencesUtil, AppConstants.QUALITY_FLAGS.DOWNLOAD));
        } else if (requestCode == AppConstants.REQUEST_SET_WALLPAPAER_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setWallpaperLogic();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return false;
    }


    /**
     * Download images in Pixtr Directory.
     *
     * @param downloadURL
     */
    public void downloadData(String downloadURL) {
        DownloadManager.Request downLoadRequest = new DownloadManager.Request(Uri.parse(downloadURL));
        downLoadRequest.setTitle("Pixtr");
        downLoadRequest.setDescription("Downloading...");
        downLoadRequest.setDestinationInExternalPublicDir("/Pixtr", data.id + ".jpg");
        downLoadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        getDownloadManager().enqueue(downLoadRequest);
    }


    /**
     * Code to set wallpaper.
     */
    public void setWallpaperLogic() {
        DownloadManager.Request downLoadRequest = new DownloadManager.Request(Uri.
                parse(appUtils.retrieveLoadURLConfig(data.urls, preferencesUtil, AppConstants.QUALITY_FLAGS.WALLPAPER)));
        downLoadRequest.setDestinationInExternalPublicDir("/Pixtr", data.id + ".jpg");
        downLoadRequest.setVisibleInDownloadsUi(false);
        downLoadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        downloadReferenceId = getDownloadManager().enqueue(downLoadRequest);
        showProgress();
    }


    /**
     * Activity skelton methods.
     */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(downloadReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(downloadReceiver);
    }


    /**
     * Broadcast receiver download completion.
     */
    BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long downloadRefId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadReferenceId == downloadRefId) {
                DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadRefId);
                Cursor cursor = getDownloadManager().query(query);
                if (cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        case DownloadManager.STATUS_SUCCESSFUL:
                            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, getDownloadManager().getUriForDownloadedFile(downloadRefId)));
                            Uri imageUri = getDownloadManager().getUriForDownloadedFile(downloadRefId);
                            try {
                                Intent wallpaperIntent = WallpaperManager.getInstance(DetailActivity.this).getCropAndSetWallpaperIntent(imageUri);
                                wallpaperIntent.setDataAndType(imageUri, AppConstants.DATA_TYPE);
                                wallpaperIntent.putExtra(AppConstants.MIME_TYPE, AppConstants.DATA_TYPE);
                                startActivityForResult(wallpaperIntent, AppConstants.WALLPAPER_INTENT_REQUEST_CODE);
                            } catch (Exception e) {
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(DetailActivity.this.getContentResolver(), imageUri);
                                    WallpaperManager.getInstance(DetailActivity.this).setBitmap(bitmap);
                                    hideProgress();
                                } catch (Exception exceptionn) {
                                    hideProgress();
                                }
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        }
    };


    /**
     * Download Manager instance helper.
     * TODO Fixed  askjxs.
     *
     * @return
     */
    public DownloadManager getDownloadManager() {
        if (downloadManager == null) {
            downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        }
        return downloadManager;
    }

}
