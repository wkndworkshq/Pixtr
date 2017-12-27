package com.tbg.pixtr.di.module;

import android.animation.ArgbEvaluator;

import com.tbg.pixtr.collection_detail.adapter.CollectionAdapter;
import com.tbg.pixtr.collection_detail.presenter.CollectionDetailPresenter;
import com.tbg.pixtr.collection_detail.view.CollectionDetailActivity;
import com.tbg.pixtr.di.scope.ActivityScope;
import com.tbg.pixtr.model.manager.NetworkManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kausthubhadhikari on 27/12/17.
 */
@Module
public class CollectionDetailModule {

    private CollectionDetailActivity activity;

    public CollectionDetailModule(CollectionDetailActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    public CollectionAdapter providesAdapter() {
        return new CollectionAdapter(activity);
    }

    @ActivityScope
    @Provides
    public CollectionDetailPresenter providesPresenter(NetworkManager manager) {
        return new CollectionDetailPresenter(activity, manager);
    }

    @ActivityScope
    @Provides
    public ArgbEvaluator providesArgbEvaluator() {
        return new ArgbEvaluator();
    }

}
