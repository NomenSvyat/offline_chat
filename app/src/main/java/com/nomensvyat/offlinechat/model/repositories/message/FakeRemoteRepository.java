package com.nomensvyat.offlinechat.model.repositories.message;

import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Single;

public class FakeRemoteRepository implements MessageRepository {
    private ArrayList<RawMessage> messageDb = new ArrayList<>();

    @Override
    public Single<List<RawMessage>> getMessages() {
        return Single.just(messageDb);
    }

    @Override
    public Single<List<RawMessage>> getMessages(long roomId) {
        return Single.just(messageDb)
                .toObservable()
                .flatMap(Observable::from)
                .filter(rawMessage -> rawMessage.getRoomId() == roomId)
                .toList()
                .first()
                .toSingle();
    }

    @Override
    public Single<RawMessage> saveMessage(RawMessage rawMessage) {
        rawMessage = rawMessage.toBuilder()
                .remoteId(getLastId() + 1)
                .build();

        messageDb.add(rawMessage);
        return Single.just(rawMessage);
    }

    private long getLastId() {
        return messageDb.size() == 0 ? 1 : messageDb.get(messageDb.size() - 1).getRemoteId();
    }
}
