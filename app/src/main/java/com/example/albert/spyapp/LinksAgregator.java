package com.example.albert.spyapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.LinkedList;

public class LinksAgregator {
    private String stalker;
    private LinkedList<String> links;
    private RequestQueue queue;
    private Context context;

    LinksAgregator(String stalker, Context context){
        this.stalker=stalker;
        links=new LinkedList<>();
        this.context=context;
        queue = Volley.newRequestQueue(context);
    }


    public void getLinks(){
        Log.d("response getting links","links");
        String url=Urls.GETLINKS.url;
        url+=stalker;
        Log.d("url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("links",response);
                        String res = response.substring(1,response.length()-2);
                        String[] parts = res.split("\\^");

                        for(int i=0;i<parts.length;i++){
                            parts[i] = parts[i].replace('_','/');
                            links.add(parts[i]);
                            Log.d("link",parts[i]);
                            System.out.println("----------");
                            System.out.println(parts[i]);
                            System.out.println("----------");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","That didn't work!");
            }
        });

        queue.add(stringRequest);

    }

    String getLink(int i){
        return links.get(i);
    }

    int getSize(){
        return links.size();
    }

}
