package com.tbg.pixtr.utils.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tbg.pixtr.AppController;
import com.tbg.pixtr.di.injector.Injector;


/**
 * Created by kausthubhadhikari on 11/11/17.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(((AppController) getApplicationContext()).getDaggerInjector());
        getPresenter().onViewCreated(savedInstanceState == null);
        statusBarLight(this.getWindow().getDecorView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().unsubscribe();
    }


    /**
     * Enable dark status bar in the activity.
     *
     * @param view
     */
    public void statusBarLight(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }


    public abstract void inject(Injector injector);

    public abstract P getPresenter();
}
