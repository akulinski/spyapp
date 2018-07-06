package com.example.albert.spyapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    private Button prev;
    private Button next;
    private ImageView photo;
    Permission permission;
    LinksAgregator links;
    int index;
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        index = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        prev = (Button)findViewById(R.id.prevButton);
        next = (Button)findViewById(R.id.nextButton);
        photo = (ImageView)findViewById(R.id.picture);

        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    links = new LinksAgregator("tomeczek",getApplicationContext());
                    links.getLinks();
                    return null;
                }
            }.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


//        permission = new Permission(this, this);
//        if (!permission.checkPermissions()) permission.request();
//        System.out.println("--------"+links.getSize()+"--------");

        if(links.getSize()!=0){
            System.out.println(links.getLink(0));
            URL url = null;
            try {
                url = new URL(links.getLink(index));
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
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
