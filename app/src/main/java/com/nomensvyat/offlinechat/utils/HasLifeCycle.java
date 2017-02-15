package com.nomensvyat.offlinechat.utils;

public interface HasLifeCycle {
    void onCreate();

    void onDestroy();

    void onStart();

    void onStop();

    void onResume();

    void onPause();
}
