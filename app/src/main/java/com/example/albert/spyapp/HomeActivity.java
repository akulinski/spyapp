package com.example.albert.spyapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        this.viewPager = (ViewPager)findViewById(R.id.container);
        setUpBottomNavbar();
        setupViewPager(this.viewPager);
    }

    private void setUpBottomNavbar(){
        BottomNavigationViewEx navbar = (BottomNavigationViewEx)findViewById(R.id.bottomHomeNavBar);
        navbar.enableAnimation(false);
        navbar.enableItemShiftingMode(false);
        navbar.enableShiftingMode(false);
        navbar.setTextVisibility(true);
    }

    private void setupViewPager(ViewPager viewPager){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SupportMapFragment.newInstance(), "camera");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }
}
