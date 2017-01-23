package com.nomensvyat.offlinechat.di.activity;

import com.nomensvyat.offlinechat.di.application.AppComponent;
import com.nomensvyat.offlinechat.ui.chat_messaging.ChatMessagingActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    void injectTo(ChatMessagingActivity chatMessagingActivity);
}
