package com.nomensvyat.offlinechat.model.entities.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class NotificationCount {
    @Id
    private long roomId;
    private int count;

    @Generated(hash = 1943221926)
    public NotificationCount(long roomId, int count) {
        this.roomId = roomId;
        this.count = count;
    }

    @Generated(hash = 2034205511)
    public NotificationCount() {
    }

    public long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
