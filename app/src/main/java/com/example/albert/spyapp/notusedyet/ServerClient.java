package com.example.albert.spyapp.notusedyet;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.URLConnection;
import java.util.concurrent.locks.ReentrantLock;

public class ServerClient implements IServerClient {
    private Context context;
    private RequestQueue queue;
    private ReentrantLock lock;

    public boolean isConnected() {
        return connected;
    }

    private boolean connected=false;

    URLConnection urlConnection;
    int counter = 0;

    ServerClient(Context con) {
        context = con;
        queue = Volley.newRequestQueue(context);
        lock = new ReentrantLock();

    }

    @Override
    public void testConnection() {

    }

    @Override
    public void getStalker(String username) {

    }

    @Override
    public void getVictim(String name) {

    }

    @Override
    public void addStalker() {

    }

    @Override
    public void addVictim() {

    }



}
