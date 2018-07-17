package com.example.albert.spyapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class ImageViewer extends AppCompatActivity {
    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 23) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Bundle b = getIntent().getExtras();
        url = b.getString("url");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);
        PhotoView photo = (PhotoView)findViewById(R.id.photo_view);
        ImageButton button = (ImageButton)findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Picasso.with(this).load(url).into(photo);
    }
}
