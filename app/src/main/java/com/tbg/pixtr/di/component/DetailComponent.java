package com.tbg.pixtr.di.component;

import com.tbg.pixtr.detail.presenter.DetailPresenter;
import com.tbg.pixtr.detail.view.DetailActivity;
import com.tbg.pixtr.di.module.DetailModule;
import com.tbg.pixtr.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by kausthubhadhikari on 31/12/17.
 */

@ActivityScope
@Component(modules = {DetailModule.class}, dependencies = {AppComponent.class})
public interface DetailComponent {

    void inject(DetailActivity activity);

    DetailPresenter providesPresenter();

}
