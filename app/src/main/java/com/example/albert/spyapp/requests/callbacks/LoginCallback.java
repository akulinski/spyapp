package com.example.albert.spyapp.requests.callbacks;

import android.content.Context;
import android.util.Log;

import com.example.albert.spyapp.cookies.Cookie;
import com.example.albert.spyapp.cookies.SingletonCookieManager;
import com.example.albert.spyapp.core.eventbus.GlobalEventBusSingleton;
import com.example.albert.spyapp.core.events.LoginEvent;
import com.example.albert.spyapp.requests.responseModels.LoginResponse;
import com.example.albert.spyapp.requests.retrofitmodels.LoginModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCallback implements Callback<JsonObject> {

    private boolean logged = false;

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    private boolean save;
    private Context context;

    public LoginCallback(boolean save,Context context){
        this.context = context;
        this.save = save;
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

        String raw = response.body().get("success").toString();

        //Log.d("LoginRes",raw);
        if (raw.equals("\"true\"")) {

            GlobalEventBusSingleton.getInstance().getEventBus().post(new LoginEvent(true));
            Log.d("rawResponse", raw);
            logged = true;
            Map<String, List<String>> headerFields = response.headers().toMultimap();

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
        }else{
            GlobalEventBusSingleton.getInstance().getEventBus().post(new LoginEvent(false));
        }

    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        Log.d("LoginFailure", t.getMessage() + " : " + call.request().body());
    }

    public boolean isLogged() {
        return logged;
    }
}
