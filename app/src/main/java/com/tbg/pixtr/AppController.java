package com.tbg.pixtr;

import android.app.Application;

import com.tbg.pixtr.di.injector.DaggerInjector;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public class AppController extends Application {

    private DaggerInjector daggerInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }


    /**
     * Retrieve Dagger instance.
     *
     * @return
     */
    public DaggerInjector getDaggerInjector() {
        if (daggerInjector == null) {
            daggerInjector = new DaggerInjector();
        }
        return daggerInjector;
    }


    /**
     * Init Dagger.
     */
    public void initDagger() {
        getDaggerInjector().init(this);
    }

}
