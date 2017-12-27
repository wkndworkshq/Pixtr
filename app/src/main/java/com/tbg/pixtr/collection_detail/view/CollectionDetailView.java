package com.tbg.pixtr.collection_detail.view;

import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.base.BaseView;

import java.util.List;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public interface CollectionDetailView extends BaseView {

    void setupView();

    void onDeliverData(List<CollectionDetailsPojo> data);

    String getId();

    void hideProgress();

    void showProgress();
}
