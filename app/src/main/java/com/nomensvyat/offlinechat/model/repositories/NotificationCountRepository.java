package com.nomensvyat.offlinechat.model.repositories;

import com.nomensvyat.offlinechat.model.entities.persistent.NotificationCount;
import com.nomensvyat.offlinechat.model.entities.persistent.NotificationCountDao;

import rx.Observable;
import rx.Single;
import timber.log.Timber;

public class NotificationCountRepository {
    private final NotificationCountDao notificationCountDao;

    public NotificationCountRepository(NotificationCountDao notificationCountDao) {
        this.notificationCountDao = notificationCountDao;
    }

    public Single<Object> cleanAllExcept(long[] roomIds) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Single<Integer> getTotalCount() {
        return notificationCountDao.rx()
                .loadAll()
                .flatMap(Observable::from)
                .reduce(0, (total, notificationCount) -> total + notificationCount.getCount())
                .toSingle();
    }

    public void reset(long roomId) {
        NotificationCount notificationCount = new NotificationCount(roomId, 0);

        notificationCountDao.save(notificationCount);
    }

    public Single<Integer> increment(long roomId) {
        return notificationCountDao.rx()
                .load(roomId)
                .filter(notificationCount -> notificationCount != null)
                .doOnNext(notificationCount -> Timber.d("Trying to increment %s",
                                                        String.valueOf(notificationCount)))
                .defaultIfEmpty(new NotificationCount(roomId, 0))
                .first()
                .toSingle()
                .flatMap(this::incrementAndSave)
                .map(NotificationCount::getCount);
    }

    private Single<NotificationCount> incrementAndSave(final NotificationCount notificationCount) {
        notificationCount.setCount(notificationCount.getCount() + 1);
        return notificationCountDao.rx()
                .save(notificationCount)
                .toSingle();
    }

}
