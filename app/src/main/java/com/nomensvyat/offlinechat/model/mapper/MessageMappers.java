package com.nomensvyat.offlinechat.model.mapper;

import com.nomensvyat.offlinechat.model.entities.message.Message;
import com.nomensvyat.offlinechat.model.entities.message.PersistentMessage;
import com.nomensvyat.offlinechat.model.entities.message.RawMessage;
import com.nomensvyat.offlinechat.utils.Provider;

public class MessageMappers {

    private static Mapper<Message, RawMessage> toRawMessageMapper;
    private static Mapper<Message, PersistentMessage> toPersistentMessageMapper;

    private MessageMappers() {
    }

    public static Mapper<Message, RawMessage> createToRawMessageMapper() {
        if (toRawMessageMapper == null) {
            toRawMessageMapper = new ToRawMessageMapper();
        }
        return toRawMessageMapper;
    }

    public static Mapper<Message, PersistentMessage> createToPersistentMessageMapper() {
        if (toPersistentMessageMapper == null) {
            toPersistentMessageMapper = new ToPersistentMapper();
        }
        return toPersistentMessageMapper;
    }

    private abstract static class MessageMapper<T extends Message> implements Mapper<Message, T> {
        private final Provider<Message.Builder<T>> builderProvider;

        private MessageMapper(Provider<Message.Builder<T>> builderProvider) {
            this.builderProvider = builderProvider;
        }

        @Override
        public T map(Message source) {
            Message.Builder<T> builder = builderProvider.provide();

            //coping required fields
            builder.roomId(source.getRoomId())
                    .message(source.getMessage())
                    .type(source.getType());

            //nullable fields
            if (source.getId() != null) {
                builder.id(source.getId());
            }

            if (source.getRemoteId() != null) {
                builder.remoteId(source.getRemoteId());
            }

            if (source.getDatetime() != null) {
                builder.datetime(source.getDatetime());
            }

            return builder.build();
        }
    }

    private static class ToRawMessageMapper extends MessageMapper<RawMessage> {
        private ToRawMessageMapper() {
            super(RawMessage::builder);
        }
    }

    private static class ToPersistentMapper extends MessageMapper<PersistentMessage> {
        private ToPersistentMapper() {
            super(PersistentMessage::builder);
        }
    }

}
