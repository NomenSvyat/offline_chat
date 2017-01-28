package com.nomensvyat.offlinechat;

import android.app.Application;
import android.os.Build;

import com.evernote.android.job.JobManager;
import com.nomensvyat.offlinechat.di.application.AppComponent;
import com.nomensvyat.offlinechat.di.application.AppModule;
import com.nomensvyat.offlinechat.di.application.DaggerAppComponent;
import com.nomensvyat.offlinechat.jobs.ChatBotJob;

import timber.log.Timber;

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

        initJobScheduler();

        initTimber();
    }

    private void initJobScheduler() {
        JobManager.create(this).addJobCreator(appComponent.getJobCreator());
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            JobManager.instance().getConfig().setAllowSmallerIntervalsForMarshmallow(true);
        }

        ChatBotJob.schedule();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {

            Timber.plant(new Timber.DebugTree());
        }
    }
}
