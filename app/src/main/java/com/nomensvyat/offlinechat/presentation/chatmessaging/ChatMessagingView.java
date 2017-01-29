package com.nomensvyat.offlinechat.presentation.chatmessaging;

import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.nomensvyat.offlinechat.model.entities.Room;
import com.nomensvyat.offlinechat.model.entities.message.Message;

import java.util.List;

public interface ChatMessagingView extends MvpView {
    void onNewMessage(Message message);

    void showMessages(List<Message> messages);

    void onMessageUpdate(@Nullable Message message);

    boolean isForRoom(Room room);
}
