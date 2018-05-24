package com.example.albert.spyapp;


import java.sql.ResultSet;

public interface IDbServer {

    boolean connect();
    ResultSet login(String username,String password);
    boolean regester(User user);
    boolean insertData(User user);

}
