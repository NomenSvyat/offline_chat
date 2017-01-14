package com.nomensvyat.offlinechat.di.application;

import dagger.Component;

@PerApplication
@Component(modules = {AppModule.class})
public interface AppComponent {
}
