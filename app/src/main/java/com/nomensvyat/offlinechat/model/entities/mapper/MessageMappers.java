package com.nomensvyat.offlinechat.model.entities.mapper;

import com.nomensvyat.offlinechat.model.entities.Message;
import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.entities.persistent.PersistentMessage;

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
        private final Message.Builder<T> builder;

        private MessageMapper(Message.Builder<T> builder) {
            this.builder = builder;
        }

        @Override
        public T map(Message source) {
            return builder
                    .id(source.getId())
                    .remoteId(source.getRemoteId())
                    .roomId(source.getRoomId())
                    .datetime(source.getDatetime())
                    .build();
        }
    }

    private static class ToRawMessageMapper extends MessageMapper<RawMessage> {
        private ToRawMessageMapper() {
            super(RawMessage.builder());
        }
    }

    private static class ToPersistentMapper extends MessageMapper<PersistentMessage> {
        private ToPersistentMapper() {
            super(PersistentMessage.builder());
        }
    }

}
