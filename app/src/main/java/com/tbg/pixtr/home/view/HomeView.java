package com.tbg.pixtr.home.view;

import com.tbg.pixtr.model.pojo.collections.CollectionsPojo;
import com.tbg.pixtr.utils.base.BaseView;

import java.util.List;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public interface HomeView extends BaseView {
    void setupView();

    void deliverData(List<CollectionsPojo> data);

    void showProgress();

    void hideProgress();
}
