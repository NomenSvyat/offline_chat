package com.nomensvyat.offlinechat.di.application;

import android.app.Application;
import android.content.Context;

import com.nomensvyat.offlinechat.di.Local;
import com.nomensvyat.offlinechat.di.Remote;
import com.nomensvyat.offlinechat.model.entities.NotificationCountDao;
import com.nomensvyat.offlinechat.model.entities.message.PersistentMessageDao;
import com.nomensvyat.offlinechat.model.repositories.NotificationCountRepository;
import com.nomensvyat.offlinechat.model.repositories.message.FakeRemoteRepository;
import com.nomensvyat.offlinechat.model.repositories.message.LocalMessageRepository;
import com.nomensvyat.offlinechat.model.repositories.message.MessageRepository;
import com.nomensvyat.offlinechat.model.services.message.MessageService;
import com.nomensvyat.offlinechat.notification.NotificationCounter;
import com.nomensvyat.offlinechat.notification.NotificationManager;
import com.nomensvyat.offlinechat.notification.OnNewMessageListener;
import com.nomensvyat.offlinechat.notification.badge.ShortcutBadgeWrapper;
import com.nomensvyat.offlinechat.utils.FakeRemoteIdProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    @PerApplication
    @Remote
    MessageRepository provideMessageRepository(FakeRemoteIdProvider remoteIdProvider) {
        return new FakeRemoteRepository(remoteIdProvider);
    }

    @Provides
    @PerApplication
    @Local
    MessageRepository provideLocalMessageRepository(PersistentMessageDao persistentMessageDao) {
        return new LocalMessageRepository(persistentMessageDao);
    }

    @Provides
    OnNewMessageListener provideOnNewMessageListener(MessageService messageService) {
        return messageService;
    }

    @Provides
    NotificationCountRepository provideNotificationCountRepository(NotificationCountDao notificationCountDao) {
        return new NotificationCountRepository(notificationCountDao);
    }

    @Provides
    @PerApplication
    NotificationCounter provideNotificationCounter(NotificationCountRepository notificationCountRepository) {
        return new NotificationCounter(new ShortcutBadgeWrapper(application),
                                       notificationCountRepository);
    }

    @Provides
    @PerApplication
    NotificationManager provideNotificationManager(NotificationCounter notificationCounter,
            MessageService messageService) {
        return new NotificationManager(application, notificationCounter, messageService);
    }
}
