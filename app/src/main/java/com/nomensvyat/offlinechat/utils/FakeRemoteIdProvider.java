package com.nomensvyat.offlinechat.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nomensvyat.offlinechat.di.application.PerApplication;

import javax.inject.Inject;

@PerApplication
public class FakeRemoteIdProvider {

    private static final String FAKE_REMOTE = "fake_remote";
    private static final String KEY_LAST_ID = "last_id";
    private final SharedPreferences lastIdContainer;

    @Inject
    public FakeRemoteIdProvider(Context context) {
        lastIdContainer = context.getSharedPreferences(FAKE_REMOTE,
                                                       Context.MODE_PRIVATE);
    }

    public long getAndIncrement() {
        int lastId = lastIdContainer.getInt(KEY_LAST_ID, 0);

        lastIdContainer.edit()
                .putInt(KEY_LAST_ID, lastId++)
                .apply();

        return lastId;
    }
}
