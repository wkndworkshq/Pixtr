package com.tbg.pixtr;

import android.app.Application;

import com.downloader.PRDownloader;
import com.evernote.android.job.JobConfig;
import com.evernote.android.job.JobManager;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.di.injector.DaggerInjector;
import com.tbg.pixtr.jobs.WallpaperJobCreator;
import com.tbg.pixtr.utils.misc.CustomJobLogger;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public class AppController extends Application {

    private DaggerInjector daggerInjector;
    private SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
    }

    public void initApp() {
        PRDownloader.initialize(this);
        sharedPreferencesUtil = new SharedPreferencesUtil(this);
        initDagger();
        jobInitializer();
    }


    /**
     * Job Creator initializer.
     */
    public void jobInitializer() {
        JobConfig.addLogger(new CustomJobLogger());
        JobManager.create(this).addJobCreator(new WallpaperJobCreator());
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
