package com.example.albert.spyapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class signUp extends AppCompatActivity {

    private Button confirm;

    private EditText login;
    private EditText email;
    private EditText password;
    private EditText repassword;
    private String passwd = "";
    private EditText debug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        confirm = findViewById(R.id.confirm_su);

        login = findViewById(R.id.login_su);
        email = findViewById(R.id.email_su);
        password = findViewById(R.id.password_su);
        repassword = findViewById(R.id.passwordagain_su);
        debug = findViewById(R.id.debug);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkpasswords()) {
                    hashPassword();
                    final ServerPost serverPost = new ServerPost(Urls.ADDSTALKER.url, v.getContext());
                    serverPost.addToPost("name", login.getText().toString());

                    //date and id are figured out by server
                    serverPost.addToPost("id", "1");
                    serverPost.addToPost("email", email.getText().toString());
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    serverPost.addToPost("dateOfJoining", timeStamp);
                    serverPost.addToPost("lastOnline", timeStamp);
                    serverPost.addToPost("password", passwd);
//                    Log.d("reg data",serverPost.getParams().toString());
                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            serverPost.postStalker();
                        }
                    });
                    th.run();

                    try {
                        th.join();
                        debug.setVisibility(View.INVISIBLE);
                        Log.d("passwordcheck",serverPost.getReturnedValue());
                        if(!serverPost.getReturnedValue().equals(" ")) {
                            Log.d("if","working");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }else{
                    debug.setText("Passwords do not match ");
                    debug.setTextColor(Color.parseColor("#FF0000"));
                    debug.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    protected void hashPassword() {
        passwd = password.getText().toString();
        passwd = Hashing.sha256().hashString(passwd, StandardCharsets.UTF_8).toString();
    }

    boolean checkpasswords() {
        return password.getText().toString().equals(repassword.getText().toString());

    }

    protected void clear(){
        login.setText("");
        password.setText("");
        repassword.setText("");
        email.setText("");
    }


}




