package com.tbg.pixtr.jobs;

import com.evernote.android.job.Job;
import com.google.gson.Gson;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.model.api.NetworkingInterface;
import com.tbg.pixtr.model.manager.NetworkManager;
import com.tbg.pixtr.utils.misc.AppConstants;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
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
    private NetworkManager networkManager;


    @Override
    protected Result onRunJob(Params params) {
        return null;
    }

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
        networkManager = new NetworkManager(networkInterface);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
        sharedPreferencesUtil.getAutoUpdateId();

    }

    public void addSubscription(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

}
