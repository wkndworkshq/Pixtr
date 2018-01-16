package com.tbg.pixtr.jobs;

import android.app.WallpaperManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.evernote.android.job.Job;
import com.google.gson.Gson;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.model.api.NetworkingInterface;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.tbg.pixtr.utils.misc.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kausthubhadhikari on 16/01/18.
 */

public class WallpaperJob extends Job {
    public static final String TAG = "WallpaaerJob";
    private CompositeDisposable compositeDisposable;
    private NetworkingInterface networkInterface;
    private String collectionId;
    private AppUtils appUtils;
    private SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    protected Result onRunJob(Params params) {
        initJob();
        return fetchData() ? Result.SUCCESS : Result.RESCHEDULE;
    }


    /**
     * Initializing the Job.
     */
    public void initJob() {
        compositeDisposable = new CompositeDisposable();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        networkInterface = retrofit.create(NetworkingInterface.class);

        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
        appUtils = new AppUtils();

        collectionId = sharedPreferencesUtil.getAutoUpdateId();
    }


    /**
     * Fetching Random Image.
     *
     * @return
     */
    public boolean fetchData() {
        boolean successFlag = false;
        Map<String, String> params = new HashMap<>();
        params.put(AppConstants.CLIENT_ID_KEY, AppConstants.CLIENT_ID);
        params.put(AppConstants.RANDOM_COLLECTION_ID_KEY, collectionId);
        try {
            Response<CollectionDetailsPojo> response = networkInterface.getRandomImage(params).execute();
            if (response.isSuccessful()) {
                successFlag = true;
            }
            CollectionDetailsPojo collectionDetailsPojo = response.body();
            Glide.with(getContext())
                    .load(appUtils.retrieveLoadURLConfig(collectionDetailsPojo.urls, sharedPreferencesUtil, AppConstants.QUALITY_FLAGS.WALLPAPER))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            try {
                                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
                                wallpaperManager.setBitmap(((BitmapDrawable) resource).getBitmap());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return successFlag;
    }

}
