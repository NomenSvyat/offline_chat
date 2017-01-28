package com.nomensvyat.offlinechat.model.repositories.message;

import android.support.annotation.CheckResult;

import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;

import java.util.List;

import rx.Single;

public interface MessageRepository {
    @CheckResult
    Single<List<RawMessage>> getMessages();

    @CheckResult
    Single<List<RawMessage>> getMessages(long roomId);

    @CheckResult
    Single<RawMessage> saveMessage(RawMessage rawMessage);

    @CheckResult
    Single<List<RawMessage>> saveMessages(List<RawMessage> rawMessages);
}
