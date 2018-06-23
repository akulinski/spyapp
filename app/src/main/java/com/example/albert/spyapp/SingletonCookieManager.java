package com.example.albert.spyapp;

import java.net.HttpCookie;
import java.util.LinkedList;

public final class SingletonCookieManager {

    private static SingletonCookieManager cookieMenager;
    private LinkedList<HttpCookie> cookies;

    private SingletonCookieManager(){

    }

    public static synchronized SingletonCookieManager getInstance(){
        if(cookieMenager==null){
            cookieMenager=new SingletonCookieManager();
        }
        return cookieMenager;
    }

    public void addCookie(HttpCookie cookie){
        cookies.add(cookie);
    }

    private HttpCookie getCookie(String id){

        for(HttpCookie cookie:cookies){
          if(id.equals(cookie.getName()))
              return cookie;
        }

        return null;
    }
}
