package com.tbg.pixtr.detail.view;

import android.os.Bundle;

import com.tbg.pixtr.R;
import com.tbg.pixtr.detail.presenter.DetailPresenter;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.utils.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements DetailView {

    @Inject
    DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        super.setStatusFlags(statusFlags.TransparentStatusBar);
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
}
