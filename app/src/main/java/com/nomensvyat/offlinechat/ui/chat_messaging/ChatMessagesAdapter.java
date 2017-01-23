package com.nomensvyat.offlinechat.ui.chat_messaging;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nomensvyat.offlinechat.BR;
import com.nomensvyat.offlinechat.databinding.ChatMessageInItemBinding;
import com.nomensvyat.offlinechat.databinding.ChatMessageOutItemBinding;
import com.nomensvyat.offlinechat.model.entities.Message;
import com.nomensvyat.offlinechat.model.entities.network.message.MessageTypes;
import com.nomensvyat.offlinechat.presentation.chatmessaging.message.ChatMessageOutViewModel;
import com.nomensvyat.offlinechat.presentation.chatmessaging.message.ChatMessageViewModel;

import java.util.ArrayList;

public class ChatMessagesAdapter
        extends RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder> {
}
