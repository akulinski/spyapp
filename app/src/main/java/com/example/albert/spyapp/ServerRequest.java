package com.example.albert.spyapp;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServerRequest {

    private URL url;
    private HttpURLConnection urlConnection;
    BufferedReader in;
    StringBuilder responseStrBuilder;


    private String returnedValue="";

    ServerRequest(String u)
    {
        try {
            url=new URL(u);
            urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.disconnect();

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
            responseStrBuilder = new StringBuilder();
            returnedValue= new Gson().fromJson(responseStrBuilder.toString(),String.class);
            addToStringBuilder();

            responseStrBuilder.delete(0, responseStrBuilder.length());


        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    private void addToStringBuilder() throws IOException {
        String inputStr;
        while ((inputStr = in.readLine()) != null)
            responseStrBuilder.append(inputStr);

        returnedValue=responseStrBuilder.toString();
    }

    public String getReturnedValue() {
        return returnedValue;
    }
}
