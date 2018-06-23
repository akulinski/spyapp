package com.example.albert.spyapp;

import java.net.HttpCookie;
import java.util.LinkedList;

public final class SingletonCookieManager {

    private static SingletonCookieManager cookieMenager;
    private LinkedList<Cookie> cookies;

    private SingletonCookieManager(){

    }

    public static synchronized SingletonCookieManager getInstance(){
        if(cookieMenager==null){
            cookieMenager=new SingletonCookieManager();
        }
        return cookieMenager;
    }

    public void addCookie(Cookie cookie){
        cookies.add(cookie);
    }

    private Cookie getCookie(String id){

        for(Cookie cookie:cookies){
          if(id.equals(cookie.getKeyValue().get("sesionid")))
              return cookie;
        }

        return null;
    }
}
