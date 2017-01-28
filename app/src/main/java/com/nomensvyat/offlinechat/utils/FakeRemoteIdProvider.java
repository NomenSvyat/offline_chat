package com.nomensvyat.offlinechat.utils;

import com.nomensvyat.offlinechat.di.application.PerApplication;

import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

@PerApplication
public class FakeRemoteIdProvider {

    private AtomicLong id = new AtomicLong(0L);

    @Inject
    public FakeRemoteIdProvider() {
    }

    public long getAndIncrement() {
        return id.getAndIncrement();
    }
}
