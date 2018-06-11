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


    private String returnedValue;

    ServerRequest(String u)
    {
        try {
            url=new URL(u);
            urlConnection = (HttpURLConnection) url.openConnection();

            in =new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            responseStrBuilder = new StringBuilder();

            addToStringBuilder();

            returnedValue= new Gson().fromJson(responseStrBuilder.toString(),String.class);

            responseStrBuilder.delete(0, responseStrBuilder.length());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void addToStringBuilder() throws IOException {
        String inputStr;
        while ((inputStr = in.readLine()) != null)
            responseStrBuilder.append(inputStr);

    }

    public String getReturnedValue() {
        return returnedValue;
    }
}
