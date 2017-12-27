package com.tbg.pixtr.detail.view;

import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.base.BaseView;

import java.util.List;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public interface DetailView extends BaseView {

    void setupView();

    void dataReceived(List<CollectionDetailsPojo> data);

    void hideProgress();

    void showProgress();

}
