package com.tbg.pixtr.di.injector;

import com.tbg.pixtr.AppController;
import com.tbg.pixtr.collection_detail.view.CollectionDetailActivity;
import com.tbg.pixtr.detail.view.DetailActivity;
import com.tbg.pixtr.di.component.AppComponent;
import com.tbg.pixtr.di.component.DaggerAppComponent;
import com.tbg.pixtr.di.component.DaggerCollectionDetailComponent;
import com.tbg.pixtr.di.component.DaggerDetailComponent;
import com.tbg.pixtr.di.component.DaggerHomeComponent;
import com.tbg.pixtr.di.module.AppModule;
import com.tbg.pixtr.di.module.CollectionDetailModule;
import com.tbg.pixtr.di.module.DetailModule;
import com.tbg.pixtr.di.module.HomeModule;
import com.tbg.pixtr.home.view.HomeActivity;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public class DaggerInjector implements Injector {
    private AppComponent appComponent;

    @Override
    public void init(AppController appController) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(appController))
                .build();
    }

    @Override
    public void inject(HomeActivity activity) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(activity))
                .build()
                .inject(activity);
    }

    @Override
    public void inject(DetailActivity activity) {
        DaggerDetailComponent.builder()
                .appComponent(appComponent)
                .detailModule(new DetailModule(activity))
                .build()
                .inject(activity);
    }

    @Override
    public void inject(CollectionDetailActivity activity) {
        DaggerCollectionDetailComponent.builder()
                .appComponent(appComponent)
                .collectionDetailModule(new CollectionDetailModule(activity))
                .build()
                .inject(activity);


    }
}
