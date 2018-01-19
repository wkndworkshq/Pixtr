package com.tbg.pixtr.utils.misc;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.evernote.android.job.util.JobLogger;

/**
 * Created by kausthubhadhikari on 18/10/17.
 */

public class CustomJobLogger implements JobLogger {
    @Override
    public void log(int priority, @NonNull String tag, @NonNull String message, @Nullable Throwable t) {
        Log.i("Job detail", "priority : " + priority + " tag " + tag + " message " + message + " exception " + t);
    }
}
