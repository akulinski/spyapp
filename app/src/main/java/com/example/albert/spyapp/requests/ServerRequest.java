package com.example.albert.spyapp.requests;

import android.content.Context;
import android.util.Log;

import com.example.albert.spyapp.cookies.Cookie;
import com.example.albert.spyapp.cookies.SingletonCookieManager;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServerRequest {
    private static final String COOKIES_HEADER = "sesionid";
    private URL url;
    private HttpURLConnection urlConnection;
    private BufferedReader in;
    private StringBuilder responseStrBuilder;
    private String returnedValue="";

    private CookieManager cookieManager = new CookieManager();
    private Context context;
    private boolean save;

    public ServerRequest(String u, Context context,boolean save)
    {
        this.context=context;
        this.save=save;
        try {
            url = new URL(u);
            urlConnection = (HttpURLConnection) url.openConnection();
            responseStrBuilder = new StringBuilder();

            CookieHandler.setDefault(cookieManager);

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

    public void login(){
        try  {
            urlConnection.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
            addToStringBuilder();
            responseStrBuilder.delete(0, responseStrBuilder.length());

            Map<String, List<String>> headerFields = urlConnection.getHeaderFields();

            Set<String> headerFieldsSet = headerFields.keySet();
            Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

            while (hearerFieldsIter.hasNext()) {
                String headerFieldKey = hearerFieldsIter.next();
                if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
                    List<String> headerFieldValue = headerFields.get(headerFieldKey);
                    for (String headerValue : headerFieldValue) {
                        Log.d("found cookie: ",headerValue);
                        Cookie cookie=new Cookie(headerValue);

                        if(save){
                            SingletonCookieManager.getInstance(context).addCookie(cookie);
                        }
                    }
                }
            }

            } catch (Exception e) {
            e.printStackTrace();
        }
        urlConnection.disconnect();
    }

    public String getReturnedValue() {
        return returnedValue;
    }

    public void setReturnedValue(String returnedValue) {
        this.returnedValue = returnedValue;
    }

}
