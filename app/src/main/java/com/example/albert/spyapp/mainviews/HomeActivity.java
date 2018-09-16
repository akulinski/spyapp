package com.example.albert.spyapp.mainviews;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.albert.spyapp.R;
import com.example.albert.spyapp.adapters.PagerAdapter;
import com.example.albert.spyapp.cookies.SingletonCookieManager;
import com.example.albert.spyapp.cordinates.CurrentLocationFragment;
import com.example.albert.spyapp.fragments.ObserveesFragment;
import com.example.albert.spyapp.fragments.SettingsFragment;
import com.example.albert.spyapp.fragments.GalleryFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationViewEx navBar;
    private PagerAdapter adapter;
    private GalleryFragment gallery;
    private ObserveesFragment observeesFragment;
    private CurrentLocationFragment currentLocationFragment;

    @SuppressLint("ClickableViewAccessibility")
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
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_maps:
                        setViewPager(0);
                        break;
                    case R.id.ic_photos:
                        setViewPager(1);
                        break;
                    case R.id.ic_observees:
                        setViewPager(2);
                        break;
                    case R.id.ic_settings:
                        setViewPager(3);
                        break;
                    case R.id.ic_logout:
                        showLogoutDialog();
                        break;

                }
                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navBar.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpBottomNavbar(){
        navBar = (BottomNavigationViewEx)findViewById(R.id.bottomHomeNavBar);
        navBar.enableAnimation(false);
        navBar.enableItemShiftingMode(false);
        navBar.enableShiftingMode(false);
        navBar.setTextVisibility(true);
    }

    private void setupViewPager(ViewPager viewPager){
        gallery = new GalleryFragment();
        currentLocationFragment = new CurrentLocationFragment();
        observeesFragment = new ObserveesFragment();
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(currentLocationFragment, "location");
        adapter.addFragment(gallery,"camera");
        adapter.addFragment(observeesFragment,"observees");
        adapter.addFragment(new SettingsFragment(),"settings");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }

    private void showLogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // LOGOUT IMPLEMENTATION
                // special dedication for Albert
                // the person that want to stay away from GUI as much as possible
                // :)
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                gallery.getAdapter().addPhoto(new Photo("https://i.pinimg.com/736x/e8/b3/a0/e8b3a0244b14d5563b3868da15bec8f7.jpg"));
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onPause() {

        SingletonCookieManager.getInstance(getApplicationContext()).saveToFile();

        super.onPause();
    }

    @Override
    protected void onStop() {

        SingletonCookieManager.getInstance(getApplicationContext()).saveToFile();

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        SingletonCookieManager.getInstance(getApplicationContext()).saveToFile();
        super.onDestroy();
    }
    @Override
    public void onBackPressed(){
        SingletonCookieManager.getInstance(getApplicationContext()).removeSession();
        super.onBackPressed();
    }

}
