package com.tbg.pixtr.di.component;

import com.google.gson.Gson;
import com.tbg.pixtr.AppController;
import com.tbg.pixtr.di.module.AppModule;
import com.tbg.pixtr.model.api.NetworkingInterface;
import com.tbg.pixtr.model.manager.NetworkManager;
import com.tbg.pixtr.utils.misc.AppUtils;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by kausthubhadhikari on 26/12/17.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void init(AppController appController);

    Gson providesGson();

    OkHttpClient providesOkHttpClient();

    Retrofit providesRetrofit();

    NetworkingInterface providesNetworkInterface();

    NetworkManager providesNetworkManager();

    AppUtils provides();

}
