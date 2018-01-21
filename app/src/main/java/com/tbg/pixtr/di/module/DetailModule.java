package com.tbg.pixtr.di.module;

import com.tbg.pixtr.detail.presenter.DetailPresenter;
import com.tbg.pixtr.detail.view.DetailActivity;
import com.tbg.pixtr.di.scope.ActivityScope;
import com.tbg.pixtr.model.manager.NetworkManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kausthubhadhikari on 31/12/17.
 */

@Module
public class DetailModule {

    private DetailActivity activity;

    public DetailModule(DetailActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    public DetailPresenter providesPresenter(NetworkManager networkManager) {
        return new DetailPresenter(activity, networkManager);
    }


}
