package com.nomensvyat.offlinechat.model.services.message;

import android.support.annotation.NonNull;

import com.nomensvyat.offlinechat.di.Local;
import com.nomensvyat.offlinechat.di.Remote;
import com.nomensvyat.offlinechat.di.application.PerApplication;
import com.nomensvyat.offlinechat.model.entities.Message;
import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.repositories.message.MessageRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.subjects.PublishSubject;

@PerApplication
public class MessageService {

    private final MessageRepository localMessageRepository;
    private final MessageRepository remoteMessageRepository;
    private PublishSubject<Message> newMessageSubject = PublishSubject.create();

    @Inject
    public MessageService(@Local MessageRepository localMessageRepository,
            @Remote MessageRepository remoteMessageRepository) {
        this.localMessageRepository = localMessageRepository;
        this.remoteMessageRepository = remoteMessageRepository;
    }

    public void onNewMessage(RawMessage rawMessage) {
        localMessageRepository.saveMessage(rawMessage)
                .subscribe(newMessageSubject::onNext);
    }

    public Observable<Message> observeNewMessages() {
        return newMessageSubject;
    }

    public Single<Message> sendMessage(RawMessage rawMessage) {
        //save unsend message
        return localMessageRepository.saveMessage(rawMessage)
                //send message
                .flatMap(remoteMessageRepository::saveMessage)
                //resave message with server generated id
                .flatMap(localMessageRepository::saveMessage);
    }

    public Observable<List<Message>> getAllMessages(final long roomId) {
        return getMessagesFromLocal(roomId)
                // TODO: 27.01.2017 wrong here. it will duplicate notification from database
                .concatWith(
                        fetchAndSave(roomId)
                                .flatMap(any -> getMessagesFromLocal(roomId))
                );
    }

    @NonNull
    private Single<List<Message>> getMessagesFromLocal(long roomId) {
        return localMessageRepository.getMessages(roomId)
                .map(this::toListOfMessages);
    }

    @NonNull
    private Single<List<RawMessage>> fetchAndSave(long roomId) {
        return remoteMessageRepository.getMessages(roomId)
                .flatMap(localMessageRepository::saveMessages);
    }

    private List<Message> toListOfMessages(List<? extends Message> source) {
        return new ArrayList<>(source);
    }
}
