package com.example.albert.spyapp.cookies;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;


/**
 *
 */
public final class SingletonCookieManager implements Serializable {

    private static SingletonCookieManager cookieMenager;
    private static String fileName = "cookie.ser";
    private CookieManagerSerializator cookieManagerSerializator;
    private Context context;

    public int check() {
        return cookies.size();
    }

    private LinkedList<Cookie> cookies;

    private SingletonCookieManager(Context context) {

        cookieManagerSerializator = new CookieManagerSerializator(context);
        cookies = cookieManagerSerializator.readJSON("cookies.json");

        if (cookies == null) {
            cookies = new LinkedList<>();
        }
    }

    public static synchronized SingletonCookieManager getInstance(Context context) {

        if (cookieMenager == null) {
            cookieMenager = new SingletonCookieManager(context);
        }
        return cookieMenager;
    }


    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public Cookie getCookie(String id) {

        for (Cookie cookie : cookies) {
            if (id.equals(cookie.getKeyValue().get("sesionid")))
                return cookie;
        }

        return null;
    }

    public void removeSession() {
        if (cookies.size() > 0) {
            this.cookies.removeLast();
        }
    }

    @Override
    public String toString() {
        return "SingletonCookieManager{" +
                "cookies=" + cookies +
                '}';
    }

    // Serializes an object and saves it to a file
    public void saveToFile() {
        cookieManagerSerializator.writeJSON(cookies, "cookies.json");
    }

}
