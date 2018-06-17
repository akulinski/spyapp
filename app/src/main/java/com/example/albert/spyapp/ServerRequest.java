package com.example.albert.spyapp;

import android.text.Editable;
import android.util.Log;

import com.google.android.gms.common.util.IOUtils;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.locks.ReentrantLock;

public class ServerRequest {
    private URL url;
    private HttpURLConnection urlConnection;
    BufferedReader in;
    StringBuilder responseStrBuilder;

    String out;
    private String returnedValue="";

    ServerRequest(String u)
    {
        try {
            url = new URL(u);
            urlConnection = (HttpURLConnection) url.openConnection();
            responseStrBuilder = new StringBuilder();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void makeTestRequest()
    {
        try {
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            returnedValue= new Gson().fromJson(responseStrBuilder.toString(),String.class);
            addToStringBuilder();

            responseStrBuilder.delete(0, responseStrBuilder.length());
            urlConnection.disconnect();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    private void addToStringBuilder() throws IOException {
        String inputStr;
        while ((inputStr = in.readLine()) != null)
            responseStrBuilder.append(inputStr);
        setReturnedValue(responseStrBuilder.toString());
    }

    public String login(){
        // dodac timeout w reqescie
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    urlConnection.setRequestMethod("GET");
                    in = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
                    addToStringBuilder();
                    out = responseStrBuilder.toString();
                    responseStrBuilder.setLength(0);
                } catch (Exception e) {
                        e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        urlConnection.disconnect();
        return out;
    }


    public String getReturnedValue() {
        return returnedValue;
    }

    public void setReturnedValue(String returnedValue) {
        this.returnedValue = returnedValue;
    }

}
