package com.example.bestdiet;

// SendMessageBody Class
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendMessageBody {
    @SerializedName("chat_id")
    private String chatId;

    @SerializedName("text")
    private String text;

    public SendMessageBody(String chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}


