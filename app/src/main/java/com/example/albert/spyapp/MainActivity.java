package com.example.albert.spyapp;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    private ReentrantLock lock;
    private  TextView textView;
    private EditText login;
    private EditText password;
    private Button logbutton;

    private Button signup;
    public static final String BROADCAST_ACTION = "com.example.albert.spyapp;";
    MyBroadCastReceiver myBroadCastReceiver=new MyBroadCastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.connected);
        signup = (Button) findViewById(R.id.regester);
        super.onResume();
        startService(new Intent(this, TestOnlineService.class));
        registerMyReceiver();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(v.getContext(), TestOnlineService.class));
                Intent intent = new Intent(v.getContext(),signUp.class);
                startActivity(intent);
            }
        });

    }


    private void registerMyReceiver() {

        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROADCAST_ACTION);
            registerReceiver(myBroadCastReceiver, intentFilter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    class MyBroadCastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            try
            {
                Log.d("received", "onReceive() called");



                boolean connected = intent.getBooleanExtra("connected",false);
                if(connected==true) {
                    textView.setText("connected");
                    textView.setTextColor(Color.parseColor("#00FF00"));
                }
                else{
                    textView.setText("not connected");
                    textView.setTextColor(Color.parseColor("#FF0000"));
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }
}



