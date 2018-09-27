package com.example.albert.spyapp.core.events;

public class LoginEvent {

    private boolean correctLogin = false;

    public LoginEvent(boolean correctLogin){
        this.correctLogin = correctLogin;
    }

    public boolean isCorrectLogin() {
        return correctLogin;
    }
}
