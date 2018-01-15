package com.tbg.pixtr.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by kausthubhadhikari on 16/01/18.
 */

public class WallpaperJobCreator implements JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case WallpaperJob.TAG:
                return new WallpaperJob();

            default:
                return null;
        }
    }
}
