package com.example.albert.spyapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.security.Policy.Parameters;
import java.util.ArrayList;
import java.util.List;

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
        permission = new Permission(this);
        permission.request();
    }

}
