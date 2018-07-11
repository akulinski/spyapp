package com.example.albert.spyapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationViewEx navbar;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        this.viewPager = (ViewPager)findViewById(R.id.container);
        setUpBottomNavbar();
        setupViewPager(this.viewPager);
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_maps:
                        setViewPager(0);
                        navbar.getMenu().getItem(0).setChecked(true);
                        break;
                    case R.id.ic_photos:
                        setViewPager(1);
                        navbar.getMenu().getItem(1).setChecked(true);
                        break;
                }
                return false;
            }
        });
    }

    private void setUpBottomNavbar(){
        navbar = (BottomNavigationViewEx)findViewById(R.id.bottomHomeNavBar);
        navbar.enableAnimation(false);
        navbar.enableItemShiftingMode(false);
        navbar.enableShiftingMode(false);
        navbar.setTextVisibility(true);
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SupportMapFragment.newInstance(), "maps");
        adapter.addFragment(new CameraActivity(),"camera");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }
}
