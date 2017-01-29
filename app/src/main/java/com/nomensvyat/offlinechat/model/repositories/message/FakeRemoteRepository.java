package com.nomensvyat.offlinechat.model.repositories.message;

import com.nomensvyat.offlinechat.model.entities.message.RawMessage;
import com.nomensvyat.offlinechat.utils.FakeRemoteIdProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

public class FakeRemoteRepository implements MessageRepository {
    private final FakeRemoteIdProvider remoteIdProvider;
    private List<RawMessage> messageDb = new ArrayList<>();

    public FakeRemoteRepository(FakeRemoteIdProvider remoteIdProvider) {
        this.remoteIdProvider = remoteIdProvider;
    }

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
                .datetime(getDateTime(rawMessage))
                .build();

        messageDb.add(rawMessage);
        return Single.just(rawMessage);
    }

    private long getDateTime(RawMessage rawMessage) {
        return rawMessage.getDatetime() == null || rawMessage.getDatetime() == 0 ?
                Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis() :
                rawMessage.getDatetime();
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
        return remoteIdProvider.getAndIncrement();
    }
}
