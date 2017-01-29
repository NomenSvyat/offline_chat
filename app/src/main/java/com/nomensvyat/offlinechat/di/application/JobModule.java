package com.nomensvyat.offlinechat.di.application;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.nomensvyat.offlinechat.di.Remote;
import com.nomensvyat.offlinechat.jobs.ChatBotJob;
import com.nomensvyat.offlinechat.model.repositories.message.MessageRepository;
import com.nomensvyat.offlinechat.notification.NotificationManager;

import java.util.Map;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class JobModule {

    @Provides
    @IntoMap
    @StringKey(ChatBotJob.TAG)
    Job provideChatBotJob(@Remote MessageRepository messageRepository,
            NotificationManager onNewMessageListener) {
        return new ChatBotJob(messageRepository, onNewMessageListener);
    }

    @PerApplication
    @Provides
    JobCreator provideJobCreator(Map<String, Provider<Job>> stringJobMap) {
        return tag -> stringJobMap.get(tag).get();
    }
}
