package com.example.albert.spyapp.cookies;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.util.LinkedList;

public class CookieManagerSerializator {
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;
    private GsonBuilder gsonb;
    private Gson mGson;

    CookieManagerSerializator(Context context){

        mSettings = context.getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        gsonb = new GsonBuilder();
        mGson = gsonb.create();
    }

    public boolean writeJSON(LinkedList<Cookie> c, String yourSettingName) {
        try {
            String writeValue = mGson.toJson(c);
            mEditor.putString(yourSettingName, writeValue);
            mEditor.commit();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public LinkedList<Cookie> readJSON(String yourSettingName) {
        String loadValue = mSettings.getString(yourSettingName, "");
        LinkedList<Cookie> c = mGson.fromJson(loadValue, LinkedList.class);
        return c;
    }
}
