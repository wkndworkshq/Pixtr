package com.tbg.pixtr.di.module;

import com.google.gson.Gson;
import com.tbg.pixtr.AppController;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.model.api.NetworkingInterface;
import com.tbg.pixtr.model.manager.NetworkManager;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.tbg.pixtr.utils.misc.AppUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kausthubhadhikari on 26/12/17.
 */
@Module
public class AppModule {

    private AppController appController;

    public AppModule(AppController appController) {
        this.appController = appController;
    }

    @Provides
    @Singleton
    public Gson providesGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(AppConstants.TIMEOUT_TIME, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public NetworkingInterface providesNetworkInterface(Retrofit retrofit) {
        return retrofit.create(NetworkingInterface.class);
    }

    @Singleton
    @Provides
    public NetworkManager providesNetworkManager(NetworkingInterface networkInterface) {
        return new NetworkManager(networkInterface);
    }

    @Singleton
    @Provides
    public AppUtils provides() {
        return new AppUtils();
    }

    @Singleton
    @Provides
    public SharedPreferencesUtil providesSharedPreferencesUtil() {
        return new SharedPreferencesUtil(appController);
    }

}
