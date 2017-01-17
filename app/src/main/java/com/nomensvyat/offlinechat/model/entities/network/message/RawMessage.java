package com.nomensvyat.offlinechat.model.entities.network.message;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RawMessage {

    public static Builder builder() {
        return new AutoValue_RawMessage.Builder();
    }

    public abstract Builder toBuilder();

    public abstract long getDatetime();

    public abstract long getId();

    public abstract String getMessage();

    public abstract long getRoomId();

    public abstract String getType();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(long id);

        public abstract Builder roomId(long id);

        public abstract Builder message(String message);

        public abstract Builder datetime(long dateTime);

        public abstract Builder type(String type);

        public abstract RawMessage build();
    }

}
