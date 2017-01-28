package com.nomensvyat.offlinechat.presentation.chatmessaging;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.text.TextUtils;

import com.nomensvyat.offlinechat.model.entities.Message;
import com.nomensvyat.offlinechat.model.entities.Room;
import com.nomensvyat.offlinechat.model.entities.network.message.MessageTypes;
import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.services.message.MessageService;
import com.nomensvyat.offlinechat.presentation.base.BasePresenter;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public final class ChatMessagingPresenter extends BasePresenter<ChatMessagingView> {

    private final MessageService messageService;

    public ChatMessagingPresenter(MessageService messageService) {
        this.messageService = messageService;
    }

    public void getMessages(long roomId) {
        addSubscription(
                messageService.getAllMessages(roomId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::showMessages)
        );

        observeNewMessages(roomId);
    }

    public void observeNewMessages(long roomId) {
        addSubscription(
                messageService.observeNewMessages()
                        .filter(message -> message.getRoomId() == roomId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onNewMessage)
        );
    }

    @UiThread
    private void showMessages(@Nullable List<Message> messages) {
        if (messages != null && isViewAttached()) {
            //noinspection ConstantConditions
            getView().showMessages(messages);
        }
    }

    @UiThread
    private void onNewMessage(@Nullable Message message) {
        if (message != null && isViewAttached()) {
            //noinspection ConstantConditions
            getView().onNewMessage(message);
        }
    }

    @UiThread
    private void updateMessage(Message message) {
        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().onMessageUpdate(message);
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

        addSubscription(
                messageService.sendMessage(rawMessage)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onNewMessage)
        );
    }
}
