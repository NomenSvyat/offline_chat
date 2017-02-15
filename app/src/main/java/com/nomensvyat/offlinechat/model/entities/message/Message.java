package com.nomensvyat.offlinechat.model.entities.message;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface Message {
    @Nullable
    Long getId();

    @Nullable
    Long getRemoteId();

    long getRoomId();

    @NonNull
    String getMessage();

    @Nullable
    Long getDatetime();

    @NonNull
    String getType();

    public interface Builder<T extends Message> {
        Builder<T> id(Long id);

        Builder<T> remoteId(Long remoteId);

        Builder<T> roomId(long id);

        Builder<T> message(@NonNull String message);

        Builder<T> datetime(Long dateTime);

        Builder<T> type(String type);

        T build();

    }
}
