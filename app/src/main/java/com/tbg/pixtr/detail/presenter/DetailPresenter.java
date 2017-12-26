package com.tbg.pixtr.detail.presenter;

import com.tbg.pixtr.detail.view.DetailView;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.base.BasePresenter;

import java.util.List;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public class DetailPresenter extends BasePresenter {

    public DetailView view;

    public DetailPresenter(DetailView view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onViewCreated(boolean isLaunched) {
        view.setupView();
        if(isLaunched){

        }
    }

    public void onDataReceived(List<CollectionDetailsPojo> data){

    }

}
