package com.example.albert.spyapp;

import android.text.Editable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.ConnectException;

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
            urlConnection.setConnectTimeout(1);

        } catch (Exception e) {
            returnedValue="error";
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

        } catch (Exception ex) {
            returnedValue="error";
        }
    }


    private void addToStringBuilder() throws IOException {
        String inputStr;
        try {
            while ((inputStr = in.readLine()) != null)
                responseStrBuilder.append(inputStr);

            returnedValue=responseStrBuilder.toString();
        }
        catch (Exception ex){
            returnedValue="error";
        }
    }

    public void login(String login, String password){
        try {
            urlConnection.setRequestProperty("Content-Type","application/json");
            OutputStream os = urlConnection.getOutputStream();
            String json =  "{\"name\": \""+login+"\",\"password\":\""+password+"\"}";
            byte[] output = json.getBytes("UTF-8");
            os.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getReturnedValue() {
        return returnedValue;
    }
}
