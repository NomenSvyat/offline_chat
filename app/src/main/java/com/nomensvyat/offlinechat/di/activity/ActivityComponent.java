package com.nomensvyat.offlinechat.di.activity;

import com.nomensvyat.offlinechat.di.application.AppComponent;

import dagger.Component;

@PerActivity
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
}
