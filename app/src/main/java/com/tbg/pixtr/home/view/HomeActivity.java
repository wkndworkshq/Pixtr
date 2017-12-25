package com.tbg.pixtr.home.view;

import android.os.Bundle;

import com.tbg.pixtr.R;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.utils.base.BaseActivity;
import com.tbg.pixtr.utils.base.BasePresenter;

import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void inject(Injector injector) {
        injector.inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onNetworkError(Throwable throwable) {

    }
}
