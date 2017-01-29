package com.nomensvyat.offlinechat.di.application;

import android.content.Context;

import com.nomensvyat.offlinechat.model.entities.NotificationCountDao;
import com.nomensvyat.offlinechat.model.entities.message.DaoMaster;
import com.nomensvyat.offlinechat.model.entities.message.DaoSession;
import com.nomensvyat.offlinechat.model.entities.message.PersistentMessageDao;
import com.nomensvyat.offlinechat.model.persistent.DatabaseOpenHelper;

import org.greenrobot.greendao.database.Database;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistentModule {
    @Provides
    DaoMaster.DevOpenHelper provideDevOpenHelper(Context context) {
        return new DatabaseOpenHelper(context);
    }

    @Provides
    DaoMaster provideDaoMaster(Database database) {
        return new DaoMaster(database);
    }

    @Provides
    Database provideDatabase(DaoMaster.DevOpenHelper devOpenHelper) {
        return devOpenHelper.getWritableDb();
    }

    @Provides
    DaoSession provideDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

    @Provides
    PersistentMessageDao providePersistentMessageDao(DaoSession daoSession) {
        return daoSession.getPersistentMessageDao();
    }

    @Provides
    NotificationCountDao provideNotificationCountDao(DaoSession daoSession) {
        return daoSession.getNotificationCountDao();
    }
}
