package com.tbg.pixtr.jobs;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.google.gson.Gson;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.model.api.NetworkingInterface;
import com.tbg.pixtr.model.manager.NetworkManager;
import com.tbg.pixtr.model.pojo.download_update.DownloadUpdatePojo;
import com.tbg.pixtr.model.pojo.job.RandomPojo;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.tbg.pixtr.utils.misc.AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kausthubhadhikari on 18/01/18.
 */

public class WallpaperProcessService extends IntentService {

    private CompositeDisposable compositeDisposable;
    private NetworkingInterface networkInterface;
    private String collectionId;
    private AppUtils appUtils;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private NetworkManager networkManager;
    private WallpaperManager wallpaperManager;


    /**
     * Factory methods of Intent Service.
     */
    public WallpaperProcessService() {
        super("WallpaperProcessService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        startForegroundService();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        startForegroundService();
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

        sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        appUtils = new AppUtils();

        collectionId = sharedPreferencesUtil.getAutoUpdateId();
        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        Map<String, String> params = new HashMap<>();
        params.put(AppConstants.CLIENT_ID_KEY, AppConstants.CLIENT_ID);
        params.put(AppConstants.RANDOM_COLLECTION_ID_KEY, collectionId);
        params.put(AppConstants.RANDOM_HEIGHT_KEY, "" + wallpaperManager.getDesiredMinimumHeight());
        params.put(AppConstants.RANDOM_WIDTH_KEY, "" + wallpaperManager.getDesiredMinimumWidth());
        addDisposable(networkInterface.getRandomImage(params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::dataReceived, this::onError));
    }


    /**
     * Handle the JSON data from the server.
     *
     * @param data
     */
    public void dataReceived(RandomPojo data) {
        setWallpaperLogic(data);
        updateDownloadStart(data.id);
    }


    /**
     * Handle the errors.
     *
     * @param throwable
     */
    public void onError(Throwable throwable) {
        Log.i("Error", "" + throwable);
    }


    /**
     * Wallpaper download and setting up logic.
     *
     * @param data
     */
    public void setWallpaperLogic(RandomPojo data) {
        File pixtrDir = new File(Environment.getExternalStorageDirectory() + "/Pixtr");
        if (!pixtrDir.exists()) {
            pixtrDir.mkdir();
        }
        PRDownloader.download(data.urls.custom, pixtrDir.getAbsolutePath(), data.id + ".jpg")
                .build()
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeFile(pixtrDir.getAbsolutePath() + "/" + data.id + ".jpg");
                            wallpaperManager.setBitmap(bitmap);
                        } catch (IOException exception) {

                        }
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });
    }


    /**
     * Download stats call for unsplash.
     *
     * @param id
     */
    public void updateDownloadStart(String id) {
        addDisposable(networkManager.updateDownloadStart(id).subscribe(this::onDeliverData, this::onError));
    }


    /**
     * Download success analytics.
     *
     * @param data
     */
    public void onDeliverData(DownloadUpdatePojo data) {
        Log.i("Download status", "" + data);
    }


    /**
     * Add disposable.
     *
     * @param disposable
     */
    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }


    /**
     * Start foreground service.
     */
    public void startForegroundService() {
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "Pixtr";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Pixtr Wallpaper",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Pixtr")
                    .setContentText("Updating Wallpaper").build();

            startForeground(1, notification);
        }
    }

}
