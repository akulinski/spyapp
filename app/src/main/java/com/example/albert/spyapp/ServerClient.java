package com.example.albert.spyapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
