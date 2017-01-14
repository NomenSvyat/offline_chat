package com.nomensvyat.offlinechat;

import android.app.Application;

import com.nomensvyat.offlinechat.di.application.AppComponent;
import com.nomensvyat.offlinechat.di.application.AppModule;
import com.nomensvyat.offlinechat.di.application.DaggerAppComponent;

public class OfflineChatApplication extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
