package com.nomensvyat.offlinechat.model.entities;

public interface Message {
    long getId();

    long getRemoteId();

    long getRoomId();

    String getMessage();

    long getDatetime();

    String getType();

    public interface Builder<T extends Message> {
        Builder<T> id(long id);

        Builder<T> remoteId(long remoteId);

        Builder<T> roomId(long id);

        Builder<T> message(String message);

        Builder<T> datetime(long dateTime);

        Builder<T> type(String type);

        T build();

    }
}
