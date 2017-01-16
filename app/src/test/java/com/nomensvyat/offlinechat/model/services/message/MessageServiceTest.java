package com.nomensvyat.offlinechat.model.services.message;

import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.repositories.message.FakeMessageRepository;

import org.junit.Test;

import rx.observers.AssertableSubscriber;

public class MessageServiceTest {

    @Test
    public void onNewMessage() {
        MessageService messageService = new MessageService(new FakeMessageRepository());

        RawMessage rawMessage = new RawMessage();

        AssertableSubscriber<RawMessage> test = messageService.observeNewMessages()
                .test();

        messageService.onNewMessage(rawMessage);

        test.assertValue(rawMessage);
    }

}