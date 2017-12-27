package com.tbg.pixtr.home.presenter;

import com.tbg.pixtr.home.view.HomeView;
import com.tbg.pixtr.model.manager.NetworkManager;
import com.tbg.pixtr.model.pojo.collections.CollectionsPojo;
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

public class HomePresenter extends BasePresenter {

    private HomeView view;
    private NetworkManager networkManager;

    public HomePresenter(HomeView baseView, NetworkManager networkManager) {
        super(baseView);
        this.view = baseView;
        this.networkManager = networkManager;
    }

    @Override
    public void onViewCreated(boolean isLaunched) {
        view.setupView();
        if (isLaunched) {
            retrieveCollections();
        }
    }

    /**
     * Retrieve data from the API.
     */
    public void retrieveCollections() {
        view.showProgress();
        Map<String, String> params = new HashMap<>();
        params.put(AppConstants.CLIENT_ID_KEY, AppConstants.CLIENT_ID);
        params.put(AppConstants.PAGE_KEY, "1");
        addDisponsable(networkManager.getCollections(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(this::onDataReceived, this::onError));
    }

    /**
     * Handle data received from the server.
     *
     * @param data
     */
    public void onDataReceived(List<CollectionsPojo> data) {
        List<CollectionsPojo> tempData = data;
        CollectionsPojo collectionsPojo = new CollectionsPojo();
        collectionsPojo.type = 0;
        tempData.add(0, collectionsPojo);
        view.deliverData(tempData);
        view.hideProgress();
    }

    /**
     * Handle  on error
     *
     * @param throwable
     */
    public void onError(Throwable throwable) {
        view.hideProgress();
        view.onNetworkError(throwable);
    }

}
