package com.example.albert.spyapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


public class GalleryFragment extends Fragment {
    private RecyclerView recyclerView;
    public static ArrayList<Photo> createLists;
    public static GalleryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_view, container, false);
        recyclerView = rootView.findViewById(R.id.gallery_grid);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        createLists = new ArrayList<>();
        createLists.add(new Photo("https://i.pinimg.com/736x/e8/b3/a0/e8b3a0244b14d5563b3868da15bec8f7.jpg"));
        createLists.add(new Photo("http://www.dailymoss.com/wp-content/uploads/2018/01/0b6b2adc21c458186ee98909e34115f5.jpg"));
        createLists.add(new Photo("http://www.dailymoss.com/wp-content/uploads/2018/01/oh-so-youre-a-programmer-tell-me-more-about-how-you-perform-your-job-without-basic-english-or-copy-p.jpg"));
        createLists.add(new Photo("https://i.pinimg.com/736x/e8/b3/a0/e8b3a0244b14d5563b3868da15bec8f7.jpg"));
        createLists.add(new Photo("https://img.buzzfeed.com/buzzfeed-static/static/2015-01/14/11/enhanced/webdr05/enhanced-21387-1421251318-7.jpg"));
        createLists.add(new Photo("https://i.pinimg.com/736x/e8/b3/a0/e8b3a0244b14d5563b3868da15bec8f7.jpg"));
        adapter = new GalleryAdapter(getContext(), createLists);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
