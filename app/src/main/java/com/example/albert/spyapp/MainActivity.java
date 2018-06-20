package com.example.albert.spyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    private ReentrantLock lock;
    private  TextView textView;
    private EditText login;
    private EditText password;
    private Button logbutton;
    private Button signupbutton;
    public static final String BROADCAST_ACTION = "com.example.albert.spyapp;";
    MyBroadCastReceiver myBroadCastReceiver=new MyBroadCastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        textView = (TextView)findViewById(R.id.connected);
        logbutton = (Button)findViewById(R.id.loginbutton);
        login = (EditText)findViewById(R.id.logintextfield);
        password = (EditText)findViewById(R.id.passwordtextfield);
        signupbutton = (Button)findViewById(R.id.signupbutton);
        logbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequest req = new ServerRequest(Urls.GETSTALKER.url+login.getText().toString()+"/"+password.getText().toString());
                password.setText("");
                login.setText("");
                System.out.println(req.login());
                if(req.login().equals("")) login.setText("nie zalogowano", TextView.BufferType.EDITABLE);
                else login.setText("zalogowano", TextView.BufferType.EDITABLE);
            }
        });
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),signUp.class);
                startActivity(i);
            }
        });
        super.onResume();
        startService(new Intent(this, TestOnlineService.class));
        registerMyReceiver();

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

    @Override
    protected void onResume() {
        super.onResume();

        startService(new Intent(this, TestOnlineService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();

        stopService(new Intent(this, TestOnlineService.class));
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



