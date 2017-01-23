package com.nomensvyat.offlinechat.ui.chat_messaging;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.nomensvyat.offlinechat.R;
import com.nomensvyat.offlinechat.databinding.ActivityChatMessagingBinding;
import com.nomensvyat.offlinechat.di.activity.ActivityComponent;
import com.nomensvyat.offlinechat.model.entities.Message;
import com.nomensvyat.offlinechat.presentation.chatmessaging.ChatMessagingPresenter;
import com.nomensvyat.offlinechat.presentation.chatmessaging.ChatMessagingView;
import com.nomensvyat.offlinechat.presentation.chatmessaging.ChatMessagingViewModel;
import com.nomensvyat.offlinechat.ui.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

public class ChatMessagingActivity extends BaseActivity<ChatMessagingView, ChatMessagingPresenter>
        implements ChatMessagingViewModel, ChatMessagingView {
    @Inject
    Lazy<ChatMessagingPresenter> presenterLazy;
    private ActivityChatMessagingBinding binding;
    private ChatMessagesAdapter chatMessagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_messaging);
        binding.setViewModel(this);

        initMessagesList();
    }

    private void initMessagesList() {
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.injectTo(this);
    }

    @NonNull
    @Override
    public ChatMessagingPresenter createPresenter() {
        return presenterLazy.get();
    }

    @Override
    public void onSendButtonClick(View view) {
        // STOPSHIP: 23.01.2017 implement
        throw new UnsupportedOperationException();
    }

    @Override
    public void onNewMessage(Message message) {
        // STOPSHIP: 23.01.2017 implement
        throw new UnsupportedOperationException();
    }

    @Override
    public void showMessages(List<Message> messages) {
        // STOPSHIP: 23.01.2017 implement
        throw new UnsupportedOperationException();
    }
}
