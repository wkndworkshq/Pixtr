package com.tbg.pixtr.detail.presenter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.tbg.pixtr.detail.view.DetailView;
import com.tbg.pixtr.model.manager.NetworkManager;
import com.tbg.pixtr.model.pojo.download_update.DownloadUpdatePojo;
import com.tbg.pixtr.utils.base.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public class DetailPresenter extends BasePresenter {

    public DetailView view;
    private NetworkManager networkManager;

    public DetailPresenter(DetailView view, NetworkManager networkManager) {
        super(view);
        this.view = view;
        this.networkManager = networkManager;
    }

    @Override
    public void onViewCreated(boolean isLaunched) {
        view.setupView();
    }


    /**
     * Just following guidelines..!!
     *
     * @param id
     */
    public void updateDownloadStart(String id) {
        addDisponsable(networkManager.updateDownloadStart(id).subscribe(this::onDeliverData, this::onErrorThrowable));
    }


    public void onDeliverData(DownloadUpdatePojo data) {
        Log.i("Download status", "" + data);
    }


    public void onErrorThrowable(Throwable throwable) {
        view.onError(throwable);
        view.hideProgressBar();
    }


    /**
     * Set Wallpaper.
     *
     * @param context
     * @param imageUri
     */
    public void setWallpaperLogic(Context context, Uri imageUri) {
        addDisponsable(Observable.fromCallable(() -> {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            WallpaperManager.getInstance(context).setBitmap(bitmap);
            return new Object();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(this::dummyObject, this::onErrorThrowable));
    }


    public void dummyObject(Object object) {
        Log.i("Log", "working");
        view.hideProgressBar();
    }


}
