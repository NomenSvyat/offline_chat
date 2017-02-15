package com.nomensvyat.offlinechat.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.nomensvyat.offlinechat.OfflineChatApplication;
import com.nomensvyat.offlinechat.di.activity.ActivityComponent;
import com.nomensvyat.offlinechat.di.activity.ActivityModule;
import com.nomensvyat.offlinechat.di.activity.DaggerActivityComponent;
import com.nomensvyat.offlinechat.di.application.AppComponent;
import com.nomensvyat.offlinechat.presentation.base.LifeCycleMvpPresenter;

public abstract class BaseActivity<V extends MvpView, P extends LifeCycleMvpPresenter<V>>
        extends MvpActivity<V, P> {

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        buildDiComponent();
        super.onCreate(savedInstanceState);

        presenter.onCreate();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.onStop();
    }
}
