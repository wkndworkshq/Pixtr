package com.tbg.pixtr.collection_detail.presenter;

import com.tbg.pixtr.collection_detail.view.CollectionDetailView;
import com.tbg.pixtr.model.manager.NetworkManager;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.base.BasePresenter;
import com.tbg.pixtr.utils.misc.AppConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public class CollectionDetailPresenter extends BasePresenter {

    private CollectionDetailView view;
    private NetworkManager networkManager;

    public CollectionDetailPresenter(CollectionDetailView baseView, NetworkManager networkManager) {
        super(baseView);
        this.view = baseView;
        this.networkManager = networkManager;
    }

    @Override
    public void onViewCreated(boolean isLaunched) {
        view.setupView();
    }

    public void requestCollectionDetails() {
        Map<String, String> params = new HashMap<>();
        params.put(AppConstants.CLIENT_ID_KEY, AppConstants.CLIENT_ID);
        params.put(AppConstants.PAGE_KEY, "1");
        addDisponsable(networkManager.getCollectionDetails(view.getId(), params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onDataReceived, this::onError));
    }

    public void onDataReceived(List<CollectionDetailsPojo> data) {
        view.hideProgress();
        view.onDeliverData(data);
    }

    public void onError(Throwable throwable) {
        view.hideProgress();
        view.onNetworkError(throwable);
    }

}
