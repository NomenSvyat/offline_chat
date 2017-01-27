package com.nomensvyat.offlinechat.model.repositories.message;

import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;

import java.util.List;

import rx.Single;

public interface MessageRepository {
    Single<List<RawMessage>> getMessages();

    Single<List<RawMessage>> getMessages(long roomId);

    Single<RawMessage> saveMessage(RawMessage rawMessage);

    Single<List<RawMessage>> saveMessages(List<RawMessage> rawMessages);
}
