package com.nomensvyat.offlinechat.di.activity;

import android.app.Activity;

import com.nomensvyat.offlinechat.presentation.chatmessaging.ChatMessagingPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    ChatMessagingPresenter providesChatMessagingPresenter() {
        return new ChatMessagingPresenter();
    }
}
