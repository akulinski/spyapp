package com.example.albert.spyapp.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.albert.spyapp.photo.Callback;
import com.example.albert.spyapp.utils.Permission;
import com.example.albert.spyapp.R;
import com.example.albert.spyapp.utils.Urls;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

public class PhotosFragment extends Fragment {
    private Button prev;
    private Button next;
    private ImageView photo;
    Permission permission;
    int index;
    private String stalker;
    static LinkedList<String> photosLinks;
    private RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_activity, parent, false);
        photosLinks = new LinkedList<>();
        this.stalker = "tomeczek";
        queue = Volley.newRequestQueue(this.getContext());
        index = 0;
        super.onCreate(savedInstanceState);
        prev = (Button)rootView.findViewById(R.id.prevButton);
        next = (Button)rootView.findViewById(R.id.nextButton);
        photo = (ImageView)rootView.findViewById(R.id.picture);

        getLinks(new Callback() {
            @Override
            public void onSuccess(LinkedList<String> links) {
                PhotosFragment.photosLinks = links;
            }

            @Override
            public void onFail(String msg) {
                System.out.println("problemik");
            }
        });




//        permission = new Permission(this, this);
//        if (!permission.checkPermissions()) permission.request();
//        System.out.println("--------"+links.getSize()+"--------");

        prev.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                try {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            if(photosLinks.size()!=0 && index >= 0){
                                URL url = null;
                                try {
                                    url = new URL(photosLinks.get(index));
                                    if(index!=0) index--;
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                Bitmap bmp = null;
                                try {
                                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                photo.setImageBitmap(bmp);
                            }
                            return null;
                        }
                    }.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                try {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            if(photosLinks.size()!=0 && index < photosLinks.size()){
                                URL url = null;
                                try {
                                    url = new URL(photosLinks.get(index));
                                    if(index!=photosLinks.size()-1) index++;
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                Bitmap bmp = null;
                                try {
                                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                photo.setImageBitmap(bmp);
                            }
                            return null;
                        }
                    }.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        return rootView;
    }


    public void getLinks(final Callback onCallBack){
        Log.d("response getting links","links");
        String url= Urls.GETLINKS.url;
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
                            photosLinks.add(parts[i]);
                            Log.d("link",parts[i]);
                            System.out.println("----------");
                            System.out.println(parts[i]);
                            System.out.println("----------");
                        }
                        onCallBack.onSuccess(photosLinks);

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
