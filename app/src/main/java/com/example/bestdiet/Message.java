package com.example.bestdiet;

public class Message {
    private int message_id;
    private From from;
    private Chat chat;
    private int date;
    private String text;

    public int getMessageId() {
        return message_id;
    }

    public void setMessageId(int message_id) {
        this.message_id = message_id;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
