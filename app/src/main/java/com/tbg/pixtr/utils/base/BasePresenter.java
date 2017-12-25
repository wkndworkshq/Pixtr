package com.tbg.pixtr.utils.base;

import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by kausthubhadhikari on 11/11/17.
 */

public abstract class BasePresenter {

    private final BaseView baseView;
    private final CompositeDisposable compositeDisposable;

    public BasePresenter(BaseView baseView) {
        this.baseView = baseView;
        compositeDisposable = new CompositeDisposable();
    }

    @CallSuper
    protected void unsubscribe() {
        compositeDisposable.clear();
    }

    protected void addDisponsable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public abstract void onViewCreated(boolean isLaunched);

}
