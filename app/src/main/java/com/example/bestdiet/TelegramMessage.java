package com.example.bestdiet;

import java.util.Date;

public class TelegramMessage {

    private String text;
    private String senderName;
    private String senderUsername;
    private boolean isFromClient; // true если сообщение от клиента, false если от пользователя


    public TelegramMessage(String text, String senderName, boolean isFromClient) {
        this.text = text;
        this.senderName = senderName;
        this.isFromClient = isFromClient;
    }

    public String getText() {
        return text;
    }

    public String getSenderName() {
        return senderName;
    }

    public boolean isFromClient() {
        return isFromClient;
    }

}

