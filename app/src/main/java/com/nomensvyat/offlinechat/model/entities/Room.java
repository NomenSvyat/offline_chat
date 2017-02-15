package com.nomensvyat.offlinechat.model.entities;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Room implements Parcelable {
    public static Room createRoom(long roomId) {
        return new AutoValue_Room(roomId);
    }

    public abstract long getRoomId();
}
