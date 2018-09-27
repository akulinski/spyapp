package com.example.albert.spyapp.mainviews;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.albert.spyapp.R;
import com.example.albert.spyapp.cookies.SingletonCookieManager;
import com.example.albert.spyapp.core.eventbus.GlobalEventBusSingleton;
import com.example.albert.spyapp.core.events.LoginEvent;
import com.example.albert.spyapp.requests.ApiClient;
import com.example.albert.spyapp.requests.ApiInterface;
import com.example.albert.spyapp.requests.callbacks.LoginCallback;
import com.example.albert.spyapp.services.GetLocationFromServerService;
import com.example.albert.spyapp.services.TestOnlineService;
import com.example.albert.spyapp.utils.Permission;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.hash.Hashing;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;

import retrofit2.Call;

public class LoginScreen extends AppCompatActivity {
    private TextView textView;
    private EditText login;
    private EditText password;
    private Button logbutton;
    private Button signupbutton;
    private CheckBox remPass;
    private EventBus eventBus;

    private ApiInterface apiService;

    public static final String BROADCAST_ACTION = "com.example.albert.spyapp;";
    private Permission permission;
    private MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
    private LoginEventHandler loginEventHandler;

    public void subscribeToEventBus() {

        eventBus = GlobalEventBusSingleton.getInstance().getEventBus();
        eventBus.register(new LoginEventHandler());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 23) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        super.onCreate(savedInstanceState);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        setContentView(R.layout.startview_activity);
        startService(new Intent(this, GetLocationFromServerService.class));
        textView = (TextView) findViewById(R.id.connected);
        logbutton = (Button) findViewById(R.id.loginbutton);
        login = (EditText) findViewById(R.id.logintextfield);
        password = (EditText) findViewById(R.id.passwordtextfield);
        signupbutton = (Button) findViewById(R.id.signupbutton);

        subscribeToEventBus();

        remPass = (CheckBox) findViewById(R.id.remPassCheckBox);

        logbutton.setOnClickListener(v -> {
            if (password.getText().toString().equals("") || login.getText().toString().equals("")) {
                showDialog("Fill in all fields");
            } else {

                LoginCallback loginCallback = new LoginCallback(remPass.isChecked(), getApplicationContext());
                Call<JsonObject> call = apiService.getLogin(login.getText().toString(), Hashing.sha256().hashString(password.getText().toString(), StandardCharsets.UTF_8).toString());
                call.enqueue(loginCallback);


                password.setText("");
                login.setText("");

            }
        });


        signupbutton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), SignUpScreen.class);
            startActivity(i);
        });

        super.onResume();
        startService(new Intent(this, TestOnlineService.class));
        registerMyReceiver();
        permission = new Permission(this, this);
        if (!permission.checkPermissions()) permission.request();
    }


    private void registerMyReceiver() {

        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROADCAST_ACTION);
            registerReceiver(myBroadCastReceiver, intentFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        int ret = SingletonCookieManager.getInstance(getApplicationContext()).check();
        Log.d("LoginSer", String.valueOf(ret));
        if (ret != 0) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        }

        startService(new Intent(this, TestOnlineService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();

        stopService(new Intent(this, TestOnlineService.class));
    }

    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            try {

                boolean connected = intent.getBooleanExtra("connected", false);
                if (connected) {
                    textView.setText("connected");
                    textView.setTextColor(Color.parseColor("#00FF00"));
                } else {
                    textView.setText("not connected");
                    textView.setTextColor(Color.parseColor("#FF0000"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void showDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

    private final class LoginEventHandler {

        @Subscribe
        public void handleLoginEvent(LoginEvent event) {

            if (!event.isCorrectLogin()) {
                showDialog("Wrong login or password");
            } else {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        }
    }
}



