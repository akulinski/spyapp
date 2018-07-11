package com.example.albert.spyapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private TextView textView,error;
    private EditText login;
    private EditText password;
    private Button logbutton;
    private Button signupbutton;
    private ServerRequest req;

    public static final String BROADCAST_ACTION = "com.example.albert.spyapp;";
    private Permission permission;
    MyBroadCastReceiver myBroadCastReceiver=new MyBroadCastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        startService(new Intent(this,GetLocationFromServerService.class));
        textView = (TextView)findViewById(R.id.connected);
        logbutton = (Button)findViewById(R.id.loginbutton);
        login = (EditText)findViewById(R.id.logintextfield);
        password = (EditText)findViewById(R.id.passwordtextfield);
        signupbutton = (Button)findViewById(R.id.signupbutton);
        error = (TextView)findViewById(R.id.error);


        logbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals("") || login.getText().toString().equals("")) {
                    showDialog("Fill in all fields");
                }
                else{
                    req = new ServerRequest(Urls.GETSTALKER.url + login.getText().toString() + "/" +
                            Hashing.sha256().hashString(password.getText().toString(), StandardCharsets.UTF_8).toString());
                    password.setText("");
                    login.setText("");
                    error.setText("");
                    try {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... params) {
                                req.login();
                                return null;
                            }
                        }.execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    System.out.println(req.getReturnedValue());
                    if (req.getReturnedValue().equals("\"\"")) {
                        showDialog("Wrong login or password");
                    } else {
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
                    }
                }
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
        permission = new Permission(this, this);
        if (!permission.checkPermissions()) permission.request();
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
                //Log.d("received", "onReceive() called");



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

    private void showDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).show();
    }

}



