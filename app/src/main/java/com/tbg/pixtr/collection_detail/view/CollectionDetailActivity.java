package com.tbg.pixtr.collection_detail.view;

import android.os.Bundle;

import com.tbg.pixtr.R;
import com.tbg.pixtr.collection_detail.presenter.CollectionDetailPresenter;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.utils.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class CollectionDetailActivity extends BaseActivity implements CollectionDetailView {

    @Inject
    CollectionDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collection_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void inject(Injector injector) {
        injector.inject(this);
    }

    @Override
    public CollectionDetailPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onNetworkError(Throwable throwable) {

    }

    @Override
    public void setupView() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }
}
