package com.example.bestdiet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TelegramMessage> messages;

    public MessagesAdapter(List<TelegramMessage> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        TelegramMessage message = messages.get(position);
        return message.isFromClient() ? 0 : 1; // 0 для клиента, 1 для пользователя
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
            return new ClientMessageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_user, parent, false);
            return new UserMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TelegramMessage message = messages.get(position);
        if (holder instanceof ClientMessageViewHolder) {
            ((ClientMessageViewHolder) holder).bind(message);
        } else if (holder instanceof UserMessageViewHolder) {
            ((UserMessageViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ClientMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView senderName;

        public ClientMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            //senderName = itemView.findViewById(R.id.senderName);
        }

        public void bind(TelegramMessage message) {
            messageText.setText(message.getText());
            //senderName.setText(message.getSenderName());
        }
    }

    static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView senderName;

        public UserMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            //senderName = itemView.findViewById(R.id.senderName);
        }

        public void bind(TelegramMessage message) {
            messageText.setText(message.getText());
            //senderName.setText(message.getSenderName());
        }
    }
}


