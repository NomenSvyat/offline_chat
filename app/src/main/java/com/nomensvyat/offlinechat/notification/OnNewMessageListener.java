package com.nomensvyat.offlinechat.notification;

import com.nomensvyat.offlinechat.model.entities.message.RawMessage;

public interface OnNewMessageListener {
    void onNewMessage(RawMessage rawMessage);
}
