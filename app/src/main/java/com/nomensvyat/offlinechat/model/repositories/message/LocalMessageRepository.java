package com.nomensvyat.offlinechat.model.repositories.message;

import com.nomensvyat.offlinechat.model.entities.message.Message;
import com.nomensvyat.offlinechat.model.entities.message.PersistentMessage;
import com.nomensvyat.offlinechat.model.entities.message.PersistentMessageDao;
import com.nomensvyat.offlinechat.model.entities.message.RawMessage;
import com.nomensvyat.offlinechat.model.mapper.MessageMappers;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static com.nomensvyat.offlinechat.model.entities.message.PersistentMessageDao.Properties.Id;
import static com.nomensvyat.offlinechat.model.entities.message.PersistentMessageDao.Properties.RemoteId;
import static com.nomensvyat.offlinechat.model.entities.message.PersistentMessageDao.Properties.RoomId;

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
        return persistentMessageDao.queryBuilder()
                .where(RoomId.eq(roomId))
                .orderAsc(RemoteId, Id)
                .rx()
                .oneByOne()
                .map(MessageMappers.createToRawMessageMapper()::map)
                .toList()
                .first()
                .toSingle();
    }

    @Override
    public Single<RawMessage> saveMessage(RawMessage rawMessage) {
        Timber.d("Saving message %s", rawMessage.toString());
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
    public Single<List<RawMessage>> saveMessages(final List<RawMessage> rawMessages) {
        Timber.d("Saving messages %s", rawMessages.toString());
        return getNewMessages(rawMessages)
                .flatMap(this::doSaveMessages);
    }

    private Single<List<RawMessage>> getNewMessages(List<RawMessage> rawMessages) {
        return Observable.from(rawMessages)
                .observeOn(Schedulers.computation())
                .map(Message::getRemoteId)
                .toList()
                .flatMap(this::getMessagesWithRemoteIds)
                .map(persistentMessages -> getDifference(rawMessages, persistentMessages))
                .first().toSingle();
    }

    private List<RawMessage> getDifference(List<RawMessage> fromList,
            List<PersistentMessage> extractList) {
        List<RawMessage> resultList = new ArrayList<>();

        outer:
        for (RawMessage rawMessage : fromList) {
            if (rawMessage.getRemoteId() == null) continue;
            for (PersistentMessage persistentMessage : extractList) {
                if (rawMessage.getRemoteId().equals(persistentMessage.getRemoteId())) {
                    continue outer;
                }
            }
            resultList.add(rawMessage);
        }

        return resultList;
    }

    private Observable<List<PersistentMessage>> getMessagesWithRemoteIds(List<Long> remoteIds) {
        return persistentMessageDao.queryBuilder()
                .where(RemoteId.in(remoteIds))
                .rx()
                .list();
    }

    private Single<List<RawMessage>> doSaveMessages(final List<RawMessage> rawMessages) {
        return Observable.from(rawMessages)
                .observeOn(Schedulers.computation())
                .map(MessageMappers.createToPersistentMessageMapper()::map)
                .toList()
                .flatMap(persistentMessages -> persistentMessageDao.rx()
                        .saveInTx(persistentMessages))
                .flatMap(Observable::from)
                .map(MessageMappers.createToRawMessageMapper()::map)
                .toList()
                .first().toSingle();
    }
}
