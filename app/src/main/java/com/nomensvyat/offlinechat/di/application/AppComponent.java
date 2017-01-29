package com.nomensvyat.offlinechat.di.application;

import com.evernote.android.job.JobCreator;
import com.nomensvyat.offlinechat.model.services.message.MessageService;
import com.nomensvyat.offlinechat.notification.NotificationManager;

import dagger.Component;

@PerApplication
@Component(modules = {AppModule.class, PersistentModule.class, JobModule.class})
public interface AppComponent {
    MessageService getMessageService();

    JobCreator getJobCreator();

    NotificationManager getNotificationManager();
}
