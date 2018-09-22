package com.example.albert.spyapp.core.events;

public class StalkerAddedEvent {
    private String message;

    public StalkerAddedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
