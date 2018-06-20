package com.example.albert.spyapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    Intent service;
    Permission permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        service = new Intent(getBaseContext(), CapturePhoto.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        button = (Button)findViewById(R.id.button);
        imageView = (ImageView)findViewById(R.id.imageView3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(service);
            }
        });
        permission = new Permission(this, this);
        if (!permission.checkPermissions()) permission.request();
    }

}
