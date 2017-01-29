package com.nomensvyat.offlinechat.notification;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import com.nomensvyat.offlinechat.model.repositories.NotificationCountRepository;
import com.nomensvyat.offlinechat.notification.badge.BadgeShower;

import rx.Single;

public class NotificationCounter {
    private final BadgeShower badgeShower;
    private final NotificationCountRepository notificationCountRepository;
    private NotificationManagerCompat notificationManager;

    public NotificationCounter(BadgeShower badgeShower,
            NotificationCountRepository notificationCountRepository,
            Context context) {
        this.badgeShower = badgeShower;
        this.notificationCountRepository = notificationCountRepository;
        notificationManager = NotificationManagerCompat.from(context);
    }

    public Single<Integer> onNotificationReceive(long room) {
        return notificationCountRepository.increment(room)
                .doOnSuccess(integer -> showBadge());
    }

    private void showBadge() {
        notificationCountRepository.getTotalCount()
                .subscribe(badgeShower::show);
    }

    public void onNotificationCancel(long room) {
        notificationCountRepository.reset(room);

        notificationManager.cancel(((Long) room).hashCode());

        showBadge();
    }

    public void recalculateForExisting(long... roomIds) {
        notificationCountRepository.cleanAllExcept(roomIds)
                .onErrorResumeNext(Single.just(null))
                .subscribe(any -> showBadge());
    }
}
