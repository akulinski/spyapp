package com.example.albert.spyapp.notusedyet;

public interface IServerClient {

    void testConnection();
    void getStalker(String username);
    void getVictim(String name);
    void addStalker();
    void addVictim();

}
