package com.nomensvyat.offlinechat.utils;

import android.support.annotation.NonNull;

public interface Provider<T> {
    @NonNull
    T provide();
}
