package com.tbg.pixtr.utils.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tbg.pixtr.AppController;
import com.tbg.pixtr.di.injector.Injector;


/**
 * Created by kausthubhadhikari on 11/11/17.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private statusFlags flags = statusFlags.NormalActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(((AppController) getApplicationContext()).getDaggerInjector());
        getPresenter().onViewCreated(savedInstanceState == null);
        setupActivityBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().unsubscribe();
    }

    public abstract void inject(Injector injector);

    public abstract P getPresenter();


    /**
     * set the flag for the activity.
     *
     * @param flags
     */
    public void setStatusFlags(statusFlags flags) {
        this.flags = flags;
    }


    /**
     * to setup Nav and Status bar according to need.
     */
    public void setupActivityBar() {
        if (flags == statusFlags.TransparentStatusBarAndNavigationBar) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else if (flags == statusFlags.LightStatusBar) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (flags == statusFlags.TransparentStatusBar) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * Enum related to the status bar, nav bar .
     */
    public enum statusFlags {
        LightStatusBar,
        TransparentStatusBar,
        TransparentStatusBarAndNavigationBar,
        NormalActivity
    }

}
