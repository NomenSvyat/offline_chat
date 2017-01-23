package com.nomensvyat.offlinechat.model.repositories.message;

import com.nomensvyat.offlinechat.model.entities.Message;
import com.nomensvyat.offlinechat.model.entities.mapper.Mapper;
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
        Mapper<Message, PersistentMessage> toPersistentMessageMapper = MessageMappers.createToPersistentMessageMapper();
        PersistentMessage persistentMessage = toPersistentMessageMapper.map(rawMessage);
        return persistentMessageDao.rx()
                .save(persistentMessage)
                .first()
                .map(MessageMappers.createToRawMessageMapper()::map)
                .toSingle();
    }
}
