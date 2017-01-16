package com.nomensvyat.offlinechat.model.services.message;

import com.nomensvyat.offlinechat.di.application.PerApplication;
import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.repositories.message.MessageRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.PublishSubject;

@PerApplication
public class MessageService {

    private final MessageRepository messageRepository;
    private PublishSubject<RawMessage> newMessageSubject = PublishSubject.create();

    @Inject
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void onNewMessage(RawMessage rawMessage) {
        messageRepository.saveMessage(rawMessage)
                .subscribe(newMessageSubject::onNext);
    }

    public Observable<RawMessage> observeNewMessages() {
        return newMessageSubject;
    }
}
