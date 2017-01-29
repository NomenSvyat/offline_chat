package com.nomensvyat.offlinechat.notification;

import android.content.Context;

import com.nomensvyat.offlinechat.model.repositories.NotificationCountRepository;
import com.nomensvyat.offlinechat.notification.badge.BadgeShower;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;

public class NotificationCounter {
    private final BadgeShower badgeShower;
    private final NotificationCountRepository notificationCountRepository;

    public NotificationCounter(BadgeShower badgeShower,
            NotificationCountRepository notificationCountRepository,
            Context context) {
        this.badgeShower = badgeShower;
        this.notificationCountRepository = notificationCountRepository;
    }

    public Single<Integer> onNotificationReceive(long room) {
        return notificationCountRepository.increment(room)
                .doOnSuccess(integer -> showBadge());
    }

    private void showBadge() {
        notificationCountRepository.getTotalCount()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(badgeShower::show);
    }

    public void onNotificationCancel(long room) {
        notificationCountRepository.reset(room);

        showBadge();
    }

    public void recalculateForExisting(long... roomIds) {
        notificationCountRepository.cleanAllExcept(roomIds)
                .onErrorResumeNext(Single.just(null))
                .subscribe(any -> showBadge());
    }
}
