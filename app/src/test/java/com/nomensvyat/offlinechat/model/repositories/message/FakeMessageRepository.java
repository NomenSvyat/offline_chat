package com.nomensvyat.offlinechat.model.repositories.message;

import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Single;

public class FakeMessageRepository implements MessageRepository {
    private ArrayList<RawMessage> rawMessages = new ArrayList<>();

    @Override

    public Single<List<RawMessage>> getMessages() {
        return Single.just(rawMessages);
    }

    @Override
    public Single<List<RawMessage>> getMessages(long roomId) {
        return Single.just(rawMessages)
                .toObservable()
                .flatMap(Observable::from)
                .filter(rawMessage -> rawMessage.getRoomId() == roomId)
                .toList()
                .first()
                .toSingle();
    }

    @Override
    public Single<RawMessage> saveMessage(RawMessage rawMessage) {
        return Single.just(rawMessage);
    }

    @Override
    public Single<List<RawMessage>> saveMessages(List<RawMessage> rawMessage) {
        return Single.just(rawMessage);
    }
}
