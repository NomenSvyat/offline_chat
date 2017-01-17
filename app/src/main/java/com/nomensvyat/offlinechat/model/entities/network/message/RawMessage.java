package com.nomensvyat.offlinechat.model.entities.network.message;

import com.google.auto.value.AutoValue;
import com.nomensvyat.offlinechat.model.entities.Message;

@AutoValue
public abstract class RawMessage implements Message {

    public static Builder builder() {
        return new AutoValue_RawMessage.Builder();
    }

    public abstract Builder toBuilder();

    @AutoValue.Builder
    public abstract static class Builder implements Message.Builder<RawMessage> {
        @Override
        public abstract Builder id(long id);

        @Override
        public abstract Builder remoteId(long remoteId);

        @Override
        public abstract Builder roomId(long id);

        @Override
        public abstract Builder message(String message);

        @Override
        public abstract Builder datetime(long dateTime);

        @Override
        public abstract Builder type(String type);

        @Override
        public abstract RawMessage build();
    }

}
