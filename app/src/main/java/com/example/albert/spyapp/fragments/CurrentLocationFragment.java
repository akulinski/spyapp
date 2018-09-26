package com.example.albert.spyapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.spyapp.R;
import com.example.albert.spyapp.core.eventbus.GlobalEventBusSingleton;
import com.example.albert.spyapp.core.events.CordinatesEvent;
import com.example.albert.spyapp.services.ServiceCheckCoordinates;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;


public class CurrentLocationFragment extends android.support.v4.app.Fragment {
    private Marker marker = null;
    MapView mMapView;
    private GoogleMap mMap;
    public static final String BROADCAST_ACTION = "com.example.albert.spyapp.gps;";

    private EventBus eventBus;

    public void registerEvent() {
        eventBus.register(new CordinatesEventHandler());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.gps, container, false);

        eventBus = GlobalEventBusSingleton.getInstance().getEventBus();
        registerEvent();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (!isConnected(getActivity()))
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
        return rootView;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
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


    private final class CordinatesEventHandler {

        @Subscribe
        public void handle(CordinatesEvent event) {

            setLocation(event.getCordinates().getX(), event.getCordinates().getY());
        }
    }
}
