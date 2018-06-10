package com.example.albert.spyapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerClient implements IServerClient {
    private Context context;
    private RequestQueue queue;
    private ReentrantLock lock;
    URLConnection urlConnection;
    int counter = 0;

    ServerClient(Context con) {
        context = con;
        queue = Volley.newRequestQueue(context);
        lock = new ReentrantLock();
    }

    @Override
    public void testConnection() {
        final String url = Urls.TEST.url;
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    lock.lock();

                    try {
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        int duration = Toast.LENGTH_SHORT;

                                        counter++;

                                        Toast toast = Toast.makeText(context,  response+ counter, duration);
                                        toast.show();
                                        Log.d("response", response);
                                    }
                                },
                                new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, error.getMessage(), duration);
                                toast.show();
                            }
                        });
                        queue.add(stringRequest);
                    } catch (SecurityException s) {
                        s.getCause();
                    } finally {
                        lock.unlock();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


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
