package com.example.albert.spyapp;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class CurrentLocation extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap = null;
    private Marker marker = null;

    private boolean networkEnabled = false;
    private Permission permission = null;
    public static final String BROADCAST_ACTION = "com.example.albert.spyapp.gps;";
    MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Asking for access to the location
        permission = new Permission(this, this);
        if (!permission.checkPermissions()) permission.request();
        networkEnabled = isNetworkEnabled();

        if(!networkEnabled) { alertbox("You are not connected!", "Please turn on your network", "Turn on", 2); }
        startService(new Intent(this, ServiceCheckCoordinates.class));
        registerMyReciver();
    }

    //Network is enable or disable
    private boolean isNetworkEnabled() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    protected void alertbox(String title, String message, String action, final int mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setCancelable(false).setTitle(title).setPositiveButton(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mode == 1) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    dialog.cancel();
                } else if (mode == 2) {
                    Intent myIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(myIntent);
                    dialog.cancel();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //setLocation("51.40089", "16.20149");
    }

    //To set the mark.s
    public void setLocation(String latitude, String longitude) {
        Double dLatitude = Double.parseDouble(latitude);
        Double dLongitude = Double.parseDouble(longitude);

        if (marker != null) marker.remove();
        LatLng current = new LatLng(dLatitude.doubleValue(), dLongitude.doubleValue());
        marker = mMap.addMarker(new MarkerOptions().position(current).title("Marker in current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
   }

    private void registerMyReciver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROADCAST_ACTION);
            registerReceiver(myBroadCastReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        startService(new Intent(this, ServiceCheckCoordinates.class));
    }

    @Override
    protected void onStop() {
        super.onStop();

        stopService(new Intent(this, ServiceCheckCoordinates.class));
    }


    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("erfewrgwretgerjkhbfwjkehfbnektrjg");
            String coordinatex = intent.getStringExtra("coordinatesx");
            String coordinatey = intent.getStringExtra("coordinatesy");
            Log.d("cordinates",coordinatex);
            System.out.println("cordinates "+coordinatex.substring(1));

            setLocation(coordinatex.substring(1), coordinatey.substring(0, coordinatey.length()-2));
        }
    }

}
