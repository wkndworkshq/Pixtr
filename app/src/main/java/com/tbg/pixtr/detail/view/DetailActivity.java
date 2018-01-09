package com.tbg.pixtr.detail.view;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.tbg.pixtr.R;
import com.tbg.pixtr.detail.presenter.DetailPresenter;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.base.BaseActivity;
import com.tbg.pixtr.utils.custom.CustomFAB;
import com.tbg.pixtr.utils.misc.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailView {

    @Inject
    DetailPresenter presenter;

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

    MaterialSheetFab materialSheetFab;

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

        customFAB.hide();
        getWindow().setStatusBarColor(Color.parseColor("#80000000"));
        materialSheetFab = new MaterialSheetFab<>(customFAB, fab_sheet, overlay, ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.colorAccent));
        locationLbl.setText(data.user.location);
        artistLbl.setText("By " + data.user.name);
        likeLbl.setText(data.likes + " Likes");
        Glide.with(this)
                .load(data.urls.full)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        customFAB.hide();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        customFAB.show();
                        return false;
                    }
                })
                .into(imageView);
    }

    @OnClick({R.id.downWallpaperImage, R.id.downWallpaperLbl, R.id.setWallpaperImage, R.id.setWallpaperLbl, R.id.viewWebsiteImage, R.id.viewWebsiteLbl})
    public void onClick(View view) {
        if (view.getId() == R.id.downWallpaperLbl || view.getId() == R.id.downWallpaperImage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_DOWNLOAD_PERMISSION);
            } else {
                downloadData(data.urls.raw);
            }
        } else if (view.getId() == R.id.setWallpaperLbl || view.getId() == R.id.setWallpaperImage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_DOWNLOAD_PERMISSION);
            } else {
                setWallpaperLogic();
            }
        } else if (view.getId() == R.id.viewWebsiteLbl || view.getId() == R.id.viewWebsiteImage) {
            Toast.makeText(this, "Website", Toast.LENGTH_SHORT).show();
        }
        materialSheetFab.hideSheet();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstants.REQUEST_DOWNLOAD_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadData(data.urls.raw);
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
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request downLoadRequest = new DownloadManager.Request(Uri.parse(downloadURL));
        downLoadRequest.setTitle("Pixtr");
        downLoadRequest.setDescription("Downloading...");
        downLoadRequest.setDestinationInExternalPublicDir("/Pixtr", data.id + ".jpg");
        downLoadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(downLoadRequest);
    }

    /**
     * Set Wallpaper logic using Intent.
     */
    public void setWallpaperLogic() {

    }

}
