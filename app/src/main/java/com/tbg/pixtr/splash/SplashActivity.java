package com.tbg.pixtr.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.tbg.pixtr.R;
import com.tbg.pixtr.home.view.HomeActivity;
import com.tbg.pixtr.utils.misc.AppConstants;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_DOWNLOAD_PERMISSION);
        } else {
            handlerImpl();
        }
    }


    /**
     * Launch the Homescreen
     */
    public void launchNextScreen() {
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    /**
     * On backpressed.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /**
     * Handler implementation
     */
    public void handlerImpl() {
        Handler handler = new Handler();
        Runnable runnable = () -> launchNextScreen();
        handler.postDelayed(runnable, AppConstants.SPLASH_SCREEN_TIME);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstants.REQUEST_DOWNLOAD_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handlerImpl();
        }else{
            finish();
        }
    }
}
