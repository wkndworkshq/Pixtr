package com.tbg.pixtr;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.di.injector.DaggerInjector;
import com.tbg.pixtr.jobs.WallpaperJob;
import com.tbg.pixtr.jobs.WallpaperJobCreator;

import java.util.concurrent.TimeUnit;

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
        sharedPreferencesUtil = new SharedPreferencesUtil(this);
        initDagger();
        jobInitializer();
    }


    /**
     * Job Creator initializer.
     */
    public void jobInitializer() {
        JobManager.create(this).addJobCreator(new WallpaperJobCreator());
        if (sharedPreferencesUtil.getAutoUpdateId().equalsIgnoreCase("empty")) {
            scheduleJob();
        }
    }


    /**
     * Schdeule a Job.
     */
    public void scheduleJob() {
        if (!JobManager.instance().getAllJobRequestsForTag(WallpaperJob.TAG).isEmpty()) {
            return;
        }

        new JobRequest.Builder(WallpaperJob.TAG)
                .setPeriodic(TimeUnit.HOURS.toMillis(24), TimeUnit.HOURS.toMillis(1))
                .build()
                .schedule();
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
