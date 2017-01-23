package com.nomensvyat.offlinechat.di.application;

import android.app.Application;
import android.content.Context;

import com.nomensvyat.offlinechat.di.Local;
import com.nomensvyat.offlinechat.di.Remote;
import com.nomensvyat.offlinechat.model.entities.persistent.PersistentMessageDao;
import com.nomensvyat.offlinechat.model.repositories.message.FakeRemoteRepository;
import com.nomensvyat.offlinechat.model.repositories.message.LocalMessageRepository;
import com.nomensvyat.offlinechat.model.repositories.message.MessageRepository;

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
    MessageRepository provideMessageRepository() {
        return new FakeRemoteRepository();
    }

    @Provides
    @PerApplication
    @Local
    MessageRepository provideLocalMessageRepository(PersistentMessageDao persistentMessageDao) {
        return new LocalMessageRepository(persistentMessageDao);
    }
}
