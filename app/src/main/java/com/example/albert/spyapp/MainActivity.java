package com.example.albert.spyapp;

import android.app.DownloadManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    private ReentrantLock lock;
    private  TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ServerClient serverClient=new ServerClient(this);

        serverClient.testConnection();

        textView=(TextView) findViewById(R.id.connected);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    if(serverClient.isConnected())
                    {
                        Log.d("Main View","true");
                        textView.setText("connected");
                        textView.setTextColor(Color.parseColor("#00FF00"));
                    }
                    else
                    {
                        Log.d("Main View","not connected");
                        textView.setText("connected");
                        textView.setTextColor(Color.parseColor("#00FF00"));
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).run();


    }
}
