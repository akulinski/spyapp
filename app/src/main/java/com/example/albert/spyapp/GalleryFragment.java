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
    private ArrayList<Photo> createLists;
    private GalleryAdapter adapter;
    public GalleryAdapter getAdapter() {
        return adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_view, container, false);
        recyclerView = rootView.findViewById(R.id.gallery_grid);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        createLists = new ArrayList<>();
        createLists.add(new Photo("https://cdn.pixabay.com/photo/2017/03/30/15/10/america-2188796_1280.jpg"));
        createLists.add(new Photo("https://fajnepodroze.pl/wp-content/uploads/2017/11/Ameryka-Polnocna-ciekawostki-dla-dzieci.jpg"));
        createLists.add(new Photo("https://i1.wp.com/1001miejsc.pl/wp-content/uploads/2016/02/Karkonosze.-Szrenica..jpg"));
        createLists.add(new Photo("http://www.kohajone.com/wp-content/uploads/2017/08/mountain-sunset.jpg"));
        createLists.add(new Photo("http://1.s.dziennik.pl/pliki/6700000/6700498-polska-krajobraz-najpiekniejsze-900-588.jpg"));
        createLists.add(new Photo("https://i2.wp.com/1001miejsc.pl/wp-content/uploads/2016/02/Polskie-morze.-Ba%C5%82tyk..jpg"));
        createLists.add(new Photo("https://static.panoramio.com.storage.googleapis.com/photos/large/66194572.jpg"));
        createLists.add(new Photo("http://s8.flog.pl/media/foto/5982637_polskie-krajobrazy.jpg"));
        createLists.add(new Photo("https://static.panoramio.com.storage.googleapis.com/photos/large/74067713.jpg"));
        createLists.add(new Photo("http://www.krajobrazypolskie.pl/images/DSC01294R.jpg"));
        createLists.add(new Photo("https://cdn.pixabay.com/photo/2015/10/29/13/30/mountains-1012368_1280.jpg"));
        createLists.add(new Photo("https://www.reisetiger.net/wp-content/uploads/2016/05/Usedom-Strandbild-Travador.jpg"));
        createLists.add(new Photo("https://d-nm.ppstatic.pl/kadr/k/r/54/c7/5a8d0b91bf870_o,size,933x0,q,70,h,55d151.jpg"));
        createLists.add(new Photo("http://m.polskiekrajobrazy.pl/i/images/stories/big/dz03MzY=_src_231992IMG.jpg"));
        createLists.add(new Photo("http://plfoto.com/zdjecia/947253.jpg"));
        adapter = new GalleryAdapter(getContext(), createLists);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
