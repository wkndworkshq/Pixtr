package com.tbg.pixtr.detail.view;

import android.os.Bundle;

import com.tbg.pixtr.R;
import com.tbg.pixtr.detail.presenter.DetailPresenter;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements DetailView {

    @Inject
    DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void inject(Injector injector) {
        injector.inject(this);
    }

    @Override
    public DetailPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onNetworkError(Throwable throwable) {

    }

    @Override
    public void setupView() {

    }

    @Override
    public void dataReceived(List<CollectionDetailsPojo> data) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }
}
