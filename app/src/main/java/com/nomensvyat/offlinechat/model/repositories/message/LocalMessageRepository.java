package com.nomensvyat.offlinechat.model.repositories.message;

import com.nomensvyat.offlinechat.model.entities.mapper.MessageMappers;
import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.entities.persistent.PersistentMessage;
import com.nomensvyat.offlinechat.model.entities.persistent.PersistentMessageDao;

import java.util.List;

import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

public class LocalMessageRepository implements MessageRepository {

    private PersistentMessageDao persistentMessageDao;

    public LocalMessageRepository(PersistentMessageDao persistentMessageDao) {this.persistentMessageDao = persistentMessageDao;}

    @Override
    public Single<List<RawMessage>> getMessages() {
        return getAllMessages()
                .observeOn(Schedulers.computation())
                .flatMap(Observable::from)
                .map(MessageMappers.createToRawMessageMapper()::map)
                .toList()
                .first()
                .toSingle();
    }

    private Observable<List<PersistentMessage>> getAllMessages() {
        return persistentMessageDao.rx()
                .loadAll();
    }

    @Override
    public Single<List<RawMessage>> getMessages(long roomId) {
        // TODO: 23.01.2017 rewrite using query
        return getAllMessages()
                .observeOn(Schedulers.computation())
                .flatMap(Observable::from)
                .filter(persistentMessage -> persistentMessage.getRoomId() == roomId)
                .map(MessageMappers.createToRawMessageMapper()::map)
                .toList()
                .first()
                .toSingle();
    }

    @Override
    public Single<RawMessage> saveMessage(RawMessage rawMessage) {
        return persistentMessageDao.rx()
                .save(toPersistentMessage(rawMessage))
                .first()
                .map(MessageMappers.createToRawMessageMapper()::map)
                .toSingle();
    }

    private PersistentMessage toPersistentMessage(RawMessage rawMessage) {
        return MessageMappers.createToPersistentMessageMapper()
                .map(rawMessage);
    }

    @Override
    public Single<List<RawMessage>> saveMessages(List<RawMessage> rawMessages) {
        return Observable.from(rawMessages)
                .observeOn(Schedulers.computation())
                .map(MessageMappers.createToPersistentMessageMapper()::map)
                .toList()
                .flatMap(persistentMessages -> persistentMessageDao.rx()
                        .saveInTx(persistentMessages))
                .flatMap(Observable::from)
                .map(MessageMappers.createToRawMessageMapper()::map)
                .toList()
                .first()
                .toSingle();
    }
}
