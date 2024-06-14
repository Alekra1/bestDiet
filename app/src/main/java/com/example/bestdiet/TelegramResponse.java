package com.example.bestdiet;

import java.util.List;

public class TelegramResponse {
    private List<Update> result;

    public List<Update> getResult() {
        return result;
    }

    public void setResult(List<Update> result) {
        this.result = result;
    }
}

