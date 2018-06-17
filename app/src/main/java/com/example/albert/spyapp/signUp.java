package com.example.albert.spyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        confirm = findViewById(R.id.confirmSignUp);

        login = findViewById(R.id.username);
        email = findViewById(R.id.enteremail);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.passwordrenter);


        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkpasswords()) {
                    hashPassword();
                    final ServerPost serverPost = new ServerPost("http://192.168.0.37:4567/stalker/addStalker", v.getContext());
                    serverPost.addToPost("name", login.getText().toString());

                    //date and id are figured out by server
                    serverPost.addToPost("id", "1");
                    serverPost.addToPost("email", email.getText().toString());
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    serverPost.addToPost("dateOfJoining", timeStamp);
                    serverPost.addToPost("lastOnline", timeStamp);
                    serverPost.addToPost("password", passwd);
                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            serverPost.postStalker();
                        }
                    });
                    th.run();
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


}




