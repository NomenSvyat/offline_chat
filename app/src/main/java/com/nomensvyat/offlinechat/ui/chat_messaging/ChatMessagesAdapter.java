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
import com.nomensvyat.offlinechat.model.entities.message.Message;
import com.nomensvyat.offlinechat.model.entities.message.MessageTypes;
import com.nomensvyat.offlinechat.presentation.chatmessaging.message.ChatMessageOutViewModel;
import com.nomensvyat.offlinechat.presentation.chatmessaging.message.ChatMessageViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.text.format.DateUtils.FORMAT_SHOW_TIME;
import static android.text.format.DateUtils.FORMAT_UTC;
import static android.text.format.DateUtils.formatDateTime;

public class ChatMessagesAdapter
        extends RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder> {
    private static final int MESSAGE_IN_TYPE = 1;
    private static final int MESSAGE_OUT_TYPE = 2;

    private ArrayList<Message> messagesList = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public void addMessages(@NonNull ArrayList<Message> messages) {
        if (this.messagesList == null || messagesList.isEmpty()) {
            setMessages(messages);
            return;
        }

        int size = this.messagesList.size();

        messagesList.addAll(messages);

        notifyItemRangeInserted(size, messages.size());
    }

    public void setMessages(@Nullable List<Message> messages) {
        messagesList = messages == null ? null : new ArrayList<>(messages);

        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        switch (viewType) {
            case MESSAGE_IN_TYPE:
                return new MessageInViewHolder(
                        ChatMessageInItemBinding.inflate(layoutInflater, parent, false));
            case MESSAGE_OUT_TYPE:
                return new MessageOutViewHolder(
                        ChatMessageOutItemBinding.inflate(layoutInflater, parent, false));
        }

        throw new IllegalStateException("View holder type mismatch");
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if (messagesList == null || messagesList.isEmpty()) {
            return;
        }
        //noinspection unchecked
        holder.setMessage(messagesList.get(position));

    }

    @Override
    public int getItemViewType(int position) {
        if (messagesList == null) {
            return 0;
        }
        Message message = messagesList.get(position);
        switch (message.getType()) {
            case MessageTypes.IN:
                return MESSAGE_IN_TYPE;
            case MessageTypes.OUT:
                return MESSAGE_OUT_TYPE;
            default:
                throw new IllegalArgumentException(
                        "Message type: " + message.getType() + " is not supported");
        }
    }

    @Override
    public int getItemCount() {
        return (messagesList == null || messagesList.isEmpty()) ? 0 : messagesList.size();
    }

    public void addMessage(@NonNull Message message) {
        if (messagesList == null) {
            messagesList = new ArrayList<>();
        }

        if (message.getId() == null) {
            throw new IllegalArgumentException("Message id must not be null at this point");
        }

        messagesList.add(message);

        notifyItemInserted(messagesList.size() - 1);
    }

    public void updateMessage(@NonNull final Message message) {
        if (messagesList == null) {
            messagesList = new ArrayList<>();
        }

        if (message.getId() == null) {
            throw new IllegalArgumentException("Message id must not be null at this point");
        }

        for (int i = 0; i < messagesList.size(); i++) {
            if (message.getId().equals(messagesList.get(i).getId())) {
                messagesList.set(i, message);
                notifyItemChanged(i);
                return;
            }
        }

        //add message if it wasn't in the list
        messagesList.add(message);
    }

    static abstract class MessageViewHolder<TBinding extends ViewDataBinding>
            extends RecyclerView.ViewHolder
            implements ChatMessageViewModel {
        protected Message message;
        protected TBinding binding;

        MessageViewHolder(final TBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public String getMessage() {
            return message.getMessage();
        }

        public void setMessage(Message message) {
            this.message = message;

            if (!binding.setVariable(BR.viewModel, this)) {
                throw new UnsupportedOperationException(
                        "Variable viewModel doesn't exist in " + binding.getClass()
                                .getCanonicalName());
            }
            binding.executePendingBindings();
        }

        @Override
        public String getTime() {
            Long datetime = message.getDatetime();
            if (datetime == null) {
                return "";
            }
            return formatDateTime(binding.getRoot().getContext(),
                                  datetime,
                                  FORMAT_SHOW_TIME | FORMAT_UTC);
        }

    }

    private static class MessageInViewHolder
            extends MessageViewHolder<ChatMessageInItemBinding> {

        MessageInViewHolder(ChatMessageInItemBinding binding) {
            super(binding);
        }
    }

    private static class MessageOutViewHolder
            extends MessageViewHolder<ChatMessageOutItemBinding>
            implements ChatMessageOutViewModel {

        MessageOutViewHolder(ChatMessageOutItemBinding binding) {
            super(binding);
        }

        @Override
        public String getStatus() {
            // STOPSHIP: 23.01.2017 add status field to message
            return "stub";
        }
    }

}
