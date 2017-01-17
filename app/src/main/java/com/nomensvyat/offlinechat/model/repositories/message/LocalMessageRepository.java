package com.nomensvyat.offlinechat.model.repositories.message;

import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;

import java.util.List;

import rx.Single;

public class LocalMessageRepository implements MessageRepository {

    @Override
    public Single<List<RawMessage>> getMessages() {
        return null;
    }

    @Override
    public Single<List<RawMessage>> getMessages(long roomId) {
        return null;
    }

    @Override
    public Single<RawMessage> saveMessage(RawMessage rawMessage) {
        return null;
    }
}
