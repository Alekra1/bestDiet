package com.example.bestdiet;

import java.util.List;

public interface UpdatesCallback {
    void onUpdatesReceived(List<Message> messages);
    void onError(Throwable t);
}

