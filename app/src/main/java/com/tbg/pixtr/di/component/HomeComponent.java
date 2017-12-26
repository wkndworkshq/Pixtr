package com.tbg.pixtr.di.component;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.tbg.pixtr.di.module.HomeModule;
import com.tbg.pixtr.di.scope.ActivityScope;
import com.tbg.pixtr.home.adapter.HomeAdapter;
import com.tbg.pixtr.home.presenter.HomePresenter;
import com.tbg.pixtr.home.view.HomeActivity;
import com.tbg.pixtr.utils.misc.HomeItemDecorator;

import dagger.Component;

/**
 * Created by kausthubhadhikari on 26/12/17.
 */
@ActivityScope
@Component(modules = {HomeModule.class}, dependencies = {AppComponent.class})
public interface HomeComponent {

    void inject(HomeActivity activity);

    HomePresenter providesPresenter();

    DefaultItemAnimator providesItemAnimator();

    LinearLayoutManager providesLayoutManager();

    HomeItemDecorator providesItemDecorator();

    HomeAdapter providesAdapter();
}
