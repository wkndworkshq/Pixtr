package com.tbg.pixtr.utils.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.tbg.pixtr.AppController;
import com.tbg.pixtr.di.injector.Injector;


/**
 * Created by kausthubhadhikari on 11/11/17.
 */

public abstract class BaseActivityTransparent<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(((AppController) getApplicationContext()).getDaggerInjector());
        getPresenter().onViewCreated(savedInstanceState == null);
        transparentStatusBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().unsubscribe();
    }

    public void transparentStatusBar(){
        Window w = getWindow(); // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public abstract void inject(Injector injector);

    public abstract P getPresenter();
}
