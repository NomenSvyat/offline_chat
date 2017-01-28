package com.nomensvyat.offlinechat.jobs;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.nomensvyat.offlinechat.model.entities.network.message.MessageTypes;
import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.repositories.message.MessageRepository;
import com.nomensvyat.offlinechat.notification.OnNewMessageListener;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class ChatBotJob extends Job {
    public static final String TAG = "CHAT_BOT_JOB";

    private final OnNewMessageListener onNewMessageListener;
    private final MessageRepository fakeRemoteRepository;
    private Calendar calendar;

    public ChatBotJob(MessageRepository fakeRemoteRepository,
            OnNewMessageListener onNewMessageListener) {
        this.fakeRemoteRepository = fakeRemoteRepository;
        this.onNewMessageListener = onNewMessageListener;
        calendar = Calendar.getInstance();
    }

    public static void schedule() {
        new JobRequest.Builder(TAG)
                .setPeriodic(TimeUnit.SECONDS.toMillis(60))
                .build()
                .schedule();
    }

    public static void disable() {
        JobManager.instance().cancelAllForTag(TAG);
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Timber.d("Bot is running...");
        RawMessage message = RawMessage.builder()
                .message("Message from bot at " + SystemClock.elapsedRealtime() / 1000)
                .datetime(calendar.getTimeInMillis())
                .roomId(1L)
                .type(MessageTypes.IN)
                .build();

        fakeRemoteRepository.saveMessage(message)
                .subscribe(onNewMessageListener::onNewMessage);

        return Result.SUCCESS;
    }
}
