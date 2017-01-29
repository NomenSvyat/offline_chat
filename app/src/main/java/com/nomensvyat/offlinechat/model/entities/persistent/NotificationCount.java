package com.nomensvyat.offlinechat.model.entities.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class NotificationCount {
    @Id
    private Long roomId;
    private int count;

    @Generated(hash = 1223550317)
    public NotificationCount(Long roomId, int count) {
        this.roomId = roomId;
        this.count = count;
    }

    @Generated(hash = 2034205511)
    public NotificationCount() {
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}