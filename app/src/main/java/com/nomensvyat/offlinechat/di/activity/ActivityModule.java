package com.nomensvyat.offlinechat.di.activity;

import android.app.Activity;

import dagger.Module;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }
}
