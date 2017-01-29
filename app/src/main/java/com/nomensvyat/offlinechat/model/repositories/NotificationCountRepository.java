package com.nomensvyat.offlinechat.model.repositories;

import rx.Single;

public class NotificationCountRepository {
    public Single<Object> cleanAllExcept(long[] roomIds) {
        return null;
    }

    public int getTotalCount() {
        return 0;
    }

    public void reset(long room) {

    }

    public Single<Integer> increment(long room) {
        return null;
    }
}
