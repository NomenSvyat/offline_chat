package com.nomensvyat.offlinechat.presentation.chatmessaging;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nomensvyat.offlinechat.model.entities.Message;
import com.nomensvyat.offlinechat.model.entities.Room;
import com.nomensvyat.offlinechat.model.entities.network.message.MessageTypes;
import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.services.message.MessageService;
import com.nomensvyat.offlinechat.presentation.base.BasePresenter;

import java.util.List;

import timber.log.Timber;

public final class ChatMessagingPresenter extends BasePresenter<ChatMessagingView> {

    private final MessageService messageService;

    public ChatMessagingPresenter(MessageService messageService) {
        this.messageService = messageService;
    }

    public void getMessages(long roomId) {
        messageService.getAllMessages(roomId)
                .subscribe(this::showMessages);
    }

    private void showMessages(@Nullable List<Message> messages) {
        if (messages != null && isViewAttached()) {
            getView().showMessages(messages);
        }
    }

    public void observeNewMessages() {
        messageService.observeNewMessages()
                .subscribe(this::showMessage);
    }

    private void showMessage(@Nullable Message message) {
        if (message != null && isViewAttached()) {
            getView().onNewMessage(message);
        }
    }

    public void sendMessage(final Room room, final String message) {
        if (TextUtils.isEmpty(message)) {
            Timber.e("Trying to send null or empty message");
            return;
        }

        RawMessage rawMessage = RawMessage.builder()
                .roomId(room.getRoomId())
                .message(message)
                .type(MessageTypes.OUT)
                .build();

        messageService.sendMessage(rawMessage)
                // TODO: 23.01.2017 add error handling
                .subscribe(this::showMessage);
    }
}
