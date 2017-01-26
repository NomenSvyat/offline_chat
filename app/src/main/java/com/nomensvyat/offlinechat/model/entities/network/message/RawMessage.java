package com.nomensvyat.offlinechat.model.entities.network.message;

import android.support.annotation.NonNull;

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
        public abstract Builder id(Long id);

        @Override
        public abstract Builder remoteId(Long remoteId);

        @Override
        public abstract Builder roomId(long id);

        @Override
        public abstract Builder message(@NonNull String message);

        @Override
        public abstract Builder datetime(Long dateTime);

        @Override
        public abstract Builder type(String type);

        @Override
        public abstract RawMessage build();
    }

}
