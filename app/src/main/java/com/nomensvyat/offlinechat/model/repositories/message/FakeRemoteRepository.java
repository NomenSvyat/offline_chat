package com.nomensvyat.offlinechat.model.repositories.message;

import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

public class FakeRemoteRepository implements MessageRepository {
    private List<RawMessage> messageDb = new ArrayList<>();

    @Override
    public Single<List<RawMessage>> getMessages() {
        return Single.just(messageDb)
                .delay(2, TimeUnit.SECONDS);
    }

    @Override
    public Single<List<RawMessage>> getMessages(long roomId) {
        return getMessages()
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

    @Override
    public Single<List<RawMessage>> saveMessages(List<RawMessage> rawMessage) {
        return Observable.from(rawMessage)
                .observeOn(Schedulers.computation())
                .concatMapDelayError(rawMessage1 -> saveMessage(rawMessage1).toObservable())
                .toList()
                .toSingle();
    }

    private long getLastId() {
        //messages save here will always have remoteId set
        //noinspection ConstantConditions
        return messageDb.size() == 0 ? 1 : messageDb.get(messageDb.size() - 1).getRemoteId();
    }
}
