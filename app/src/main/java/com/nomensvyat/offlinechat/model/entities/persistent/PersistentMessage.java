package com.nomensvyat.offlinechat.model.entities.persistent;

import com.nomensvyat.offlinechat.model.entities.Message;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class PersistentMessage implements Message {
    @Id(autoincrement = true)
    private long id;
    private long remoteId;
    private long datetime;
    private long roomId;
    private String message;
    private String type;

    @Generated(hash = 1539568952)
    public PersistentMessage(long id, long remoteId, long datetime, long roomId,
            String message, String type) {
        this.id = id;
        this.remoteId = remoteId;
        this.datetime = datetime;
        this.roomId = roomId;
        this.message = message;
        this.type = type;
    }

    @Generated(hash = 1124559793)
    public PersistentMessage() {
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getRemoteId() {
        return this.remoteId;
    }

    public void setRemoteId(long remoteId) {
        this.remoteId = remoteId;
    }

    @Override
    public long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public long getDatetime() {
        return this.datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Builder implements Message.Builder<PersistentMessage> {
        private PersistentMessage persistentMessage;

        public Builder() {
            persistentMessage = new PersistentMessage();
        }

        @Override
        public Builder id(long id) {
            persistentMessage.setId(id);
            return this;
        }

        @Override
        public Builder remoteId(long remoteId) {
            persistentMessage.setRemoteId(remoteId);
            return this;
        }

        @Override
        public Builder roomId(long id) {
            persistentMessage.setRoomId(id);
            return this;
        }

        @Override
        public Builder message(String message) {
            persistentMessage.setMessage(message);
            return this;
        }

        @Override
        public Builder datetime(long dateTime) {
            persistentMessage.setDatetime(dateTime);
            return this;
        }

        @Override
        public Builder type(String type) {
            persistentMessage.setType(type);
            return this;
        }

        @Override
        public PersistentMessage build() {
            return persistentMessage;
        }
    }

}
