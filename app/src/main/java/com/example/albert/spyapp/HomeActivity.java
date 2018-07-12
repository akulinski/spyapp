package com.example.albert.spyapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationViewEx navbar;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 23) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

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
                    case R.id.ic_observees:
                        setViewPager(2);
                        navbar.getMenu().getItem(2).setChecked(true);
                        break;
                    case R.id.ic_settings:
                        setViewPager(3);
                        navbar.getMenu().getItem(3).setChecked(true);
                        break;
                    case R.id.ic_logout:
                        setViewPager(4);
                        navbar.getMenu().getItem(4).setChecked(true);
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
        adapter.addFragment(new PhotosFragment(),"camera");
        adapter.addFragment(new ObserveesFragment(),"observees");
        adapter.addFragment(new SettingsFragment(),"settings");
        adapter.addFragment(new LogoutFragment(),"logout");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }
}
