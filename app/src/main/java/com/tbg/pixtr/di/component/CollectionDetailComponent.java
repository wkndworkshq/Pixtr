package com.tbg.pixtr.di.component;

import android.animation.ArgbEvaluator;

import com.tbg.pixtr.collection_detail.adapter.CollectionAdapter;
import com.tbg.pixtr.collection_detail.presenter.CollectionDetailPresenter;
import com.tbg.pixtr.collection_detail.view.CollectionDetailActivity;
import com.tbg.pixtr.di.module.CollectionDetailModule;
import com.tbg.pixtr.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by kausthubhadhikari on 27/12/17.
 */
@ActivityScope
@Component(modules = {CollectionDetailModule.class}, dependencies = {AppComponent.class})
public interface CollectionDetailComponent {

    void inject(CollectionDetailActivity activity);

    CollectionAdapter providesAdapter();

    CollectionDetailPresenter providesPresenter();

    ArgbEvaluator providesArgbEvaluator();
}
