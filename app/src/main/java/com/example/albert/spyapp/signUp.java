package com.example.albert.spyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class signUp extends AppCompatActivity {

    private Button confirm;

    private EditText login;
    private EditText email;
    private EditText password;
    private EditText repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        confirm=(Button) findViewById(R.id.confirmSignUp);

        login=(EditText) findViewById(R.id.username);
        email=(EditText) findViewById(R.id.enteremail);
        password=(EditText) findViewById(R.id.password);
        repassword=(EditText) findViewById(R.id.passwordrenter);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ServerPost serverPost=new ServerPost("http://192.168.0.37:4567/stalker/addStalker",v.getContext());
                serverPost.addToPost("name",login.getText().toString());
                serverPost.addToPost("id","1");
                serverPost.addToPost("password",password.getText().toString());
                serverPost.addToPost("email",email.getText().toString());
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                serverPost.addToPost("dateOfJoining",timeStamp );
                serverPost.addToPost("lastOnline", timeStamp);

               Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        serverPost.postStalker();
                    }
                });
                th.run();
                try {
                    th.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
