package com.tbg.pixtr.detail.presenter;

import android.util.Log;

import com.tbg.pixtr.detail.view.DetailView;
import com.tbg.pixtr.model.manager.NetworkManager;
import com.tbg.pixtr.model.pojo.download_update.DownloadUpdatePojo;
import com.tbg.pixtr.utils.base.BasePresenter;

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
    }


}
