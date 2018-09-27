package com.example.albert.spyapp.core.events;

public class StalkerAddingErrorEvent extends Throwable {

    public StalkerAddingErrorEvent(String message) {
        this.message = message;
    }

    public StalkerAddingErrorEvent(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public StalkerAddingErrorEvent(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }

    public StalkerAddingErrorEvent(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public StalkerAddingErrorEvent(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private String message;
}
