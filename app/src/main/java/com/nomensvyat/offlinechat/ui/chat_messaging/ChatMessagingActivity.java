package com.nomensvyat.offlinechat.ui.chat_messaging;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.nomensvyat.offlinechat.R;
import com.nomensvyat.offlinechat.databinding.ActivityChatMessagingBinding;
import com.nomensvyat.offlinechat.di.activity.ActivityComponent;
import com.nomensvyat.offlinechat.model.entities.Message;
import com.nomensvyat.offlinechat.model.entities.Room;
import com.nomensvyat.offlinechat.presentation.chatmessaging.ChatMessagingPresenter;
import com.nomensvyat.offlinechat.presentation.chatmessaging.ChatMessagingView;
import com.nomensvyat.offlinechat.presentation.chatmessaging.ChatMessagingViewModel;
import com.nomensvyat.offlinechat.ui.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

public class ChatMessagingActivity extends BaseActivity<ChatMessagingView, ChatMessagingPresenter>
        implements ChatMessagingViewModel, ChatMessagingView {
    private static final String KEY_ROOM = "ROOM";
    @Inject
    Lazy<ChatMessagingPresenter> presenterLazy;
    private ActivityChatMessagingBinding binding;
    private ChatMessagesAdapter chatMessagesAdapter;
    private Room room;

    public static Intent createStartIntent(Context context, Room room) {
        return new Intent(context, ChatMessagingActivity.class)
                .putExtra(KEY_ROOM, room);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        readExtras();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_messaging);
        binding.setViewModel(this);

        initMessagesList();

        RxTextView.textChanges(binding.inputLayout.messageEditText)
                .map(charSequence -> !TextUtils.isEmpty(charSequence))
                .distinctUntilChanged()
                .subscribe(binding.inputLayout.sendButton::setEnabled);
    }

    private void readExtras() {
        room = getIntent().getParcelableExtra(KEY_ROOM);

        if (room == null) {
            throw new RuntimeException("Room extra is equal to null");
        }
    }

    private void initMessagesList() {
        chatMessagesAdapter = new ChatMessagesAdapter();

        binding.chatMessageList.setAdapter(chatMessagesAdapter);
        binding.chatMessageList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
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
        String message = binding.inputLayout.messageEditText.getText().toString().trim();

        if (TextUtils.isEmpty(message)) {
            return;
        }

        presenter.sendMessage(room, message);
    }

    @Override
    public void onNewMessage(Message message) {
        if (message == null) {
            return;
        }

        chatMessagesAdapter.addMessage(message);
    }

    @Override
    public void showMessages(List<Message> messages) {
        chatMessagesAdapter.setMessages(messages);
    }

    @Override
    public void onMessageUpdate(@Nullable Message message) {
        if (message == null) {
            return;
        }

        chatMessagesAdapter.updateMessage(message);
    }
}
