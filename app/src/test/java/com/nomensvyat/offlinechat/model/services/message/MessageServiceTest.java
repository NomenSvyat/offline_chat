package com.nomensvyat.offlinechat.model.services.message;

import com.nomensvyat.offlinechat.model.entities.message.Message;
import com.nomensvyat.offlinechat.model.entities.message.MessageTypes;
import com.nomensvyat.offlinechat.model.entities.message.RawMessage;
import com.nomensvyat.offlinechat.model.repositories.message.FakeMessageRepository;

import org.junit.Test;

import java.util.Calendar;

import rx.observers.AssertableSubscriber;

public class MessageServiceTest {

    @Test
    public void onNewMessage() {
        MessageService messageService = new MessageService(new FakeMessageRepository(),
                                                           new FakeMessageRepository());

        RawMessage rawMessage = RawMessage.builder()
                .id(1L)
                .message("some message")
                .datetime(Calendar.getInstance().getTimeInMillis())
                .roomId(1)
                .type(MessageTypes.IN)
                .build();

        AssertableSubscriber<Message> test = messageService.observeNewMessages()
                .test();

        messageService.onNewMessage(rawMessage);

        test.assertValue(rawMessage);
    }

}