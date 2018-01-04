package com.tbg.pixtr.detail.presenter;

import com.tbg.pixtr.detail.view.DetailView;
import com.tbg.pixtr.utils.base.BasePresenter;

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
    }



}
