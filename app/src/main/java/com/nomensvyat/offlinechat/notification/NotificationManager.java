package com.nomensvyat.offlinechat.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.nomensvyat.offlinechat.R;
import com.nomensvyat.offlinechat.model.entities.Room;
import com.nomensvyat.offlinechat.model.entities.network.message.RawMessage;
import com.nomensvyat.offlinechat.model.services.message.MessageService;
import com.nomensvyat.offlinechat.ui.chat_messaging.ChatMessagingActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class NotificationManager implements OnNewMessageListener {
    private static final int NOTIFICATION_REQ_CODE = 12;
    protected final Context context;
    private final NotificationCounter notificationCounter;
    private final MessageService messageService;
    private NotificationHandler notificationHandler;

    public NotificationManager(Context context,
            NotificationCounter notificationCounter,
            MessageService messageService) {
        this.context = context;
        this.notificationCounter = notificationCounter;
        this.messageService = messageService;
    }

    @Override
    public void onNewMessage(RawMessage rawMessage) {
        messageService.onNewMessage(rawMessage);
        Observable.fromCallable(
                () -> notificationHandler != null
                        && notificationHandler.handleNotification(rawMessage))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.computation())
                .filter(consumed -> !consumed)
                .subscribe(any -> doOnNotificationReceive(rawMessage), Throwable::printStackTrace);
    }

    private void doOnNotificationReceive(RawMessage rawMessage) {
        if (!needToShowNotification(rawMessage)) {
            return;
        }

        notificationCounter.onNotificationReceive(rawMessage.getRoomId())
                .subscribe(count -> showNotification(rawMessage, count),
                           t -> Timber.e(t, "Error while counting notifications"));
    }

    /**
     * Unused for now. Can be used for system notifications that doesn't need to be shown
     */
    protected boolean needToShowNotification(RawMessage rawMessage) {
        return true;
    }

    private void showNotification(RawMessage rawMessage, int count) {
        long roomId = rawMessage.getRoomId();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.new_message_for_room,
                                                   String.valueOf(roomId)))
                .setNumber(count)
                .setContentIntent(getPendingIntent(roomId))
                .setContentText(rawMessage.getMessage());

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(Long.valueOf(roomId).hashCode(),
                                         notificationBuilder.build());
    }

    private PendingIntent getPendingIntent(long roomId) {
        Intent startIntent = ChatMessagingActivity.createStartIntent(context,
                                                                     Room.createRoom(roomId));
        //this is for intentEquals method, so notification for new room won't update existing
        startIntent.setData(Uri.parse("room " + roomId));
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return PendingIntent.getActivity(context,
                                         NOTIFICATION_REQ_CODE,
                                         startIntent,
                                         PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void registerNotificationHandler(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    public void unregisterNotificationHandler(NotificationHandler notificationHandler) {
        if (this.notificationHandler == notificationHandler) {
            this.notificationHandler = null;
        }
    }

    @Nullable
    protected NotificationHandler getNotificationHandler() {
        return notificationHandler;
    }

    public void hideNotifications(Room room) {
        notificationCounter.onNotificationCancel(room.getRoomId());

        NotificationManagerCompat.from(context).cancel(Long.valueOf(room.getRoomId()).hashCode());
    }
}
