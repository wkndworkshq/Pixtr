package com.tbg.pixtr.detail.view;

import com.tbg.pixtr.utils.base.BaseView;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public interface DetailView extends BaseView {

    void setupView();

    void onError(Throwable throwable);

    void hideProgressBar();

}
