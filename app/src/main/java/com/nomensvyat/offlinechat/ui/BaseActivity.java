package com.nomensvyat.offlinechat.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.nomensvyat.offlinechat.OfflineChatApplication;
import com.nomensvyat.offlinechat.di.activity.ActivityComponent;
import com.nomensvyat.offlinechat.di.activity.ActivityModule;
import com.nomensvyat.offlinechat.di.activity.DaggerActivityComponent;
import com.nomensvyat.offlinechat.di.application.AppComponent;

public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpActivity<V, P> {

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        buildDiComponent();
        super.onCreate(savedInstanceState);
    }

    private void buildDiComponent() {
        AppComponent appComponent = OfflineChatApplication.getAppComponent();

        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build();

        inject(activityComponent);
    }

    /**
     * Inject dependencies into activity with provided {@link ActivityComponent}
     */
    protected abstract void inject(ActivityComponent activityComponent);
}
