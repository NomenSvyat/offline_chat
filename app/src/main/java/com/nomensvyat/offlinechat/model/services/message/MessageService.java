package com.nomensvyat.offlinechat.model.services.message;

import com.nomensvyat.offlinechat.di.Local;
import com.nomensvyat.offlinechat.di.Remote;
import com.nomensvyat.offlinechat.di.application.PerApplication;
import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.repositories.message.MessageRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.subjects.PublishSubject;

@PerApplication
public class MessageService {

    private final MessageRepository localMessageRepository;
    private final MessageRepository remoteMessageRepository;
    private PublishSubject<RawMessage> newMessageSubject = PublishSubject.create();

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

    public Observable<RawMessage> observeNewMessages() {
        return newMessageSubject;
    }

    public Single<RawMessage> sendMessage(RawMessage rawMessage) {
        //save unsend message
        return localMessageRepository.saveMessage(rawMessage)
                //send message
                .flatMap(remoteMessageRepository::saveMessage)
                //resave message with server generated id
                .flatMap(localMessageRepository::saveMessage);
    }
}
