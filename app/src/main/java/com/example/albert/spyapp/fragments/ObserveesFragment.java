package com.example.albert.spyapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.albert.spyapp.utils.Observee;
import com.example.albert.spyapp.adapters.ObserveesAdapter;
import com.example.albert.spyapp.R;
import java.util.ArrayList;

public class ObserveesFragment extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    ArrayList<Observee> createLists;
    ObserveesAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_view, container, false);
        recyclerView = rootView.findViewById(R.id.gallery_grid);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        createLists = new ArrayList<>();
        createLists.add(new Observee("Konradek","#4286f4"));
        createLists.add(new Observee("Albercik","#437a58"));
        createLists.add(new Observee("Tomeczek","#ffbb00"));
        adapter = new ObserveesAdapter(getContext(), createLists);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
