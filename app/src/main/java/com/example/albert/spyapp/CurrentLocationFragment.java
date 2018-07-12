package com.example.albert.spyapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class CurrentLocationFragment extends android.support.v4.app.Fragment {
    private Marker marker = null;
    MapView mMapView;
    private GoogleMap mMap;
    public static final String BROADCAST_ACTION = "com.example.albert.spyapp.gps;";
    MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gps, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(!isConnected(getActivity()))
        alertbox("No Internet connection.", "You have no internet connection", "Turn on", 2);


        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                // mMap.setMyLocationEnabled(false);
                setLocation("51.40089", "16.20149");
            }
        });

        getActivity().startService(new Intent(getActivity(), ServiceCheckCoordinates.class));
        registerMyReceiver();
        return rootView;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    protected void alertbox(String title, String message, String action, final int mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

//To set the mark.
public void setLocation(String latitude, String longitude) {
    Double dLatitude = Double.parseDouble(latitude);
    Double dLongitude = Double.parseDouble(longitude);

    if (marker != null) marker.remove();
    LatLng current = new LatLng(dLatitude.doubleValue(), dLongitude.doubleValue());
    marker = mMap.addMarker(new MarkerOptions().position(current).title("Marker in current location"));
    CameraPosition cameraPosition = new CameraPosition.Builder().target(current).zoom(12).build();
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
}

    private void registerMyReceiver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROADCAST_ACTION);
            getActivity().registerReceiver(myBroadCastReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().startService(new Intent(getActivity(), ServiceCheckCoordinates.class));
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().stopService(new Intent(getActivity(), ServiceCheckCoordinates.class));
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
