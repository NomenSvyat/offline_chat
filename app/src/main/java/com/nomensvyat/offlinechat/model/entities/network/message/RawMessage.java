package com.nomensvyat.offlinechat.model.entities.network.message;

public class RawMessage {
    private long id;
    private long roomId;
    private long senderId;
    private String message;
    private long dateTime;
    private String type;
    private String status;

    public RawMessage() {
    }

    public long getDatetime() {
        return dateTime;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public long getRoomId() {
        return roomId;
    }

    public long getSenderId() {
        return senderId;
    }

    public String getType() {
        return type;
    }

}
