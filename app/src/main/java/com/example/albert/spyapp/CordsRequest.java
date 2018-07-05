package com.example.albert.spyapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CordsRequest {

    private String url;
    private RequestQueue queue;
    CordsRequest(String url,Context context){
        this.url=url;
        queue = Volley.newRequestQueue(context);

    }

    void getCords(){
        Log.d("response getting cords","cords");
        Log.d("url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Log.d("Cords",response);
                        String[] parts = response.split(" ");
                        String cordinatesx = parts[0];
                        String cordinatesy = parts[1];
                        Cordinates cordinates=new Cordinates(cordinatesx,cordinatesy);
                        LastLocationSingleton.getInstance().setLastLocation(cordinates);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","That didn't work!");
            }
        });

        queue.add(stringRequest);

    }

}
