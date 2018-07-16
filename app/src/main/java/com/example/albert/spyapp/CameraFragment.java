package com.example.albert.spyapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class CameraFragment extends Fragment {
    ImageView img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_view, container, false);
        img = rootView.findViewById(R.id.obrazeczek);
        Picasso.with(getContext()).load("https://i.imgur.com/1JQuXvJ.jpg").into(img);
        return rootView;

    }
}
