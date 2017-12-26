package com.tbg.pixtr.di.module;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.tbg.pixtr.di.scope.ActivityScope;
import com.tbg.pixtr.home.adapter.HomeAdapter;
import com.tbg.pixtr.home.presenter.HomePresenter;
import com.tbg.pixtr.home.view.HomeActivity;
import com.tbg.pixtr.model.manager.NetworkManager;
import com.tbg.pixtr.utils.misc.AppUtils;
import com.tbg.pixtr.utils.misc.HomeItemDecorator;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kausthubhadhikari on 26/12/17.
 */
@Module
public class HomeModule {

    private HomeActivity activity;

    public HomeModule(HomeActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    public HomePresenter providesPresenter(NetworkManager networkManager) {
        return new HomePresenter(activity, networkManager);
    }

    @ActivityScope
    @Provides
    public DefaultItemAnimator providesItemAnimator() {
        return new DefaultItemAnimator();
    }

    @ActivityScope
    @Provides
    public LinearLayoutManager providesLayoutManager() {
        return new LinearLayoutManager(activity);
    }

    @ActivityScope
    @Provides
    public HomeItemDecorator providesItemDecorator(AppUtils appUtils) {
        return new HomeItemDecorator(appUtils.pxToDp(4));
    }

    @ActivityScope
    @Provides
    public HomeAdapter providesAdapter() {
        return new HomeAdapter(activity);
    }
}
