package com.nomensvyat.offlinechat.presentation.chatmessaging;

import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.nomensvyat.offlinechat.model.entities.Message;

import java.util.List;

public interface ChatMessagingView extends MvpView {
    void onNewMessage(Message message);

    void showMessages(List<Message> messages);

    void onMessageUpdate(@Nullable Message message);
}
