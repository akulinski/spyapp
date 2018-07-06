package com.example.albert.spyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainView extends AppCompatActivity {
    ImageButton maps;
    ImageButton camera;
    ImageButton sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Spyapp</font>"));
        maps = (ImageButton)findViewById(R.id.maps);
        camera = (ImageButton)findViewById(R.id.camera);

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CurrentLocation.class);
                startActivity(i);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(i);
            }
        });

    }
}
