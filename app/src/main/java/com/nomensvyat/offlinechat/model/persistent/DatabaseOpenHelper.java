package com.nomensvyat.offlinechat.model.persistent;

import android.content.Context;

import com.nomensvyat.offlinechat.model.entities.message.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class DatabaseOpenHelper extends DaoMaster.DevOpenHelper {
    private static final String DB_NAME = "offline_chat";

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //// STOPSHIP: 17.01.2017 no upgrade now. Just drop recreate tables
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
