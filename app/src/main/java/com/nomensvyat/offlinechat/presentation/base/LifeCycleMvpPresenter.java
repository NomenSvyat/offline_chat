package com.nomensvyat.offlinechat.presentation.base;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.nomensvyat.offlinechat.utils.HasLifeCycle;

public interface LifeCycleMvpPresenter<V extends MvpView> extends MvpPresenter<V>, HasLifeCycle {
}
