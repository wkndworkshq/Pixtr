package com.tbg.pixtr.collection_detail.view;

import android.os.Bundle;

import com.tbg.pixtr.R;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.utils.base.BaseActivity;
import com.tbg.pixtr.utils.base.BasePresenter;

public class CollectionDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);
    }

    @Override
    public void inject(Injector injector) {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onNetworkError(Throwable throwable) {

    }
}
