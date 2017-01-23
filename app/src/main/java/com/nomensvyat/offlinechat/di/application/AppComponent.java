package com.nomensvyat.offlinechat.di.application;

import dagger.Component;

@PerApplication
@Component(modules = {AppModule.class, PersistentModule.class})
public interface AppComponent {
}
