package com.nomensvyat.offlinechat.presentation.base;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.nomensvyat.offlinechat.utils.rx.LifecycleCompositeSubscription;
import com.nomensvyat.offlinechat.utils.rx.LifecycleCompositeSubscriptionFabric;

import rx.Subscription;

/**
 * Can be used later for adding something like lifecycle methods.
 * Now it's no-op
 */
public abstract class BasePresenter<V extends MvpView>
        extends MvpBasePresenter<V>
        implements LifeCycleMvpPresenter<V> {
    private LifecycleCompositeSubscription compositeSubscription = LifecycleCompositeSubscriptionFabric
            .create();

    protected final void addSubscription(Subscription subscription) {
        compositeSubscription.addSubscription(subscription);
    }

    @Override
    public void onCreate() {compositeSubscription.onCreate();}

    @Override
    public void onDestroy() {compositeSubscription.onDestroy();}

    @Override
    public void onStart() {compositeSubscription.onStart();}

    @Override
    public void onStop() {compositeSubscription.onStop();}

    @Override
    public void onResume() {compositeSubscription.onResume();}

    @Override
    public void onPause() {compositeSubscription.onPause();}
}
