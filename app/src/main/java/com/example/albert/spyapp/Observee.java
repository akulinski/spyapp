package com.example.albert.spyapp;

public class Observee {
    private String login;
    private String colour;

    public Observee(String login, String colour) {
        this.login = login;
        this.colour = colour;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


}
