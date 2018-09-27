package com.example.albert.spyapp.requests.retrofitmodels;

public class LoginModel {

    private boolean logged;

    public LoginModel(boolean logged) {
        this.logged = logged;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
