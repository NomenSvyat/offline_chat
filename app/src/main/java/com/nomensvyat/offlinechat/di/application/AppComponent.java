package com.nomensvyat.offlinechat.di.application;

import com.nomensvyat.offlinechat.model.services.message.MessageService;

import dagger.Component;

@PerApplication
@Component(modules = {AppModule.class, PersistentModule.class})
public interface AppComponent {
    MessageService getMessageService();
}
