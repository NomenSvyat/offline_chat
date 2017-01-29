package com.nomensvyat.offlinechat.notification;

import android.support.annotation.NonNull;

import com.nomensvyat.offlinechat.model.entities.message.RawMessage;

public interface NotificationHandler {
    /**
     * Check and handle notification
     *
     * @return true if notification consumed, else false
     */
    boolean handleNotification(@NonNull RawMessage rawMessage);
}
