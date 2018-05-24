package com.example.albert.spyapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

public class DbServer implements IDbServer {
    private Socket DbServerConnection;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    DbServer()
    {

    }

    @Override
    public boolean connect() {

        boolean flag=false;

        try {
            DbServerConnection = new Socket("localhost",6789);
            inputStream=new ObjectInputStream(DbServerConnection.getInputStream());
            outputStream=new ObjectOutputStream(DbServerConnection.getOutputStream());
            flag=true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if(!flag) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connect();
            }
        }

        return false;
    }

    @Override
    public ResultSet login(String username, String password) {



        return null;
    }

    @Override
    public boolean regester(User user) {
        return false;
    }

    @Override
    public boolean insertData(User user) {
        return false;
    }
}
