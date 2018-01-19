package com.tbg.pixtr.jobs;

import android.content.Intent;
import android.os.Build;

import com.evernote.android.job.Job;

/**
 * Created by kausthubhadhikari on 16/01/18.
 */

public class WallpaperJob extends Job {
    public static final String TAG = "WallpaaerJob";

    @Override
    protected Result onRunJob(Params params) {
        Intent intent = new Intent(getContext(), WallpaperProcessService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getContext().startForegroundService(intent);
        } else {
            getContext().startService(intent);
        }
        return Result.SUCCESS;
    }

}
