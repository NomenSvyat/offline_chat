package com.nomensvyat.offlinechat.utils.rx;

import com.nomensvyat.offlinechat.utils.HasLifeCycle;

import rx.Subscription;

public interface LifecycleCompositeSubscription extends HasLifeCycle {
    void addSubscription(Subscription subscription);
}
