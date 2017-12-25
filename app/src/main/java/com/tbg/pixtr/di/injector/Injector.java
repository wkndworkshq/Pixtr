package com.tbg.pixtr.di.injector;

import com.tbg.pixtr.AppController;
import com.tbg.pixtr.home.view.HomeActivity;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public interface Injector {

    void init(AppController appController);

    void inject(HomeActivity activity);

}
