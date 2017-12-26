package com.tbg.pixtr.collection_detail.presenter;

import com.tbg.pixtr.collection_detail.view.CollectionDetailView;
import com.tbg.pixtr.utils.base.BasePresenter;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public class CollectionDetailPresenter extends BasePresenter {

    private CollectionDetailView view;

    public CollectionDetailPresenter(CollectionDetailView baseView) {
        super(baseView);
        this.view = baseView;
    }

    @Override
    public void onViewCreated(boolean isLaunched) {
        view.setupView();
        if (isLaunched) {
            requestCollectionDetails();
        }
    }

    public void requestCollectionDetails(){

    }
}
