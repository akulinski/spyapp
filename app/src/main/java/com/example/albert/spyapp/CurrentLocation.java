package com.example.albert.spyapp;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class CurrentLocation extends Activity implements View.OnClickListener {
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    private Button getLocation = null;
    private TextView editLocation = null;
    private ProgressBar progressBar = null;

    private boolean gpsEnabled = false;
    private boolean networkEnabled = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        editLocation = (TextView) findViewById(R.id.editTextLocation);
        editLocation.setKeyListener(null);

        getLocation = (Button) findViewById(R.id.btnLocation);
        getLocation.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Asking for access to the location
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {
        gpsEnabled = isLocationEnabled();
        networkEnabled = isNetworkEnabled();
        if (gpsEnabled) {
            editLocation.setText("Move your device!\nPlease wait!");
            progressBar.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        } else {
            alertbox("GPS Status!!", "Your GPS is off!", "GPS on", 1);
        }

        if (!networkEnabled) alertbox("Network", "Your network is off!", "Turn on", 2);
    }

    //GPS is enable or disable
    private boolean isLocationEnabled() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;
        } else {
            return false;
        }
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

    //Listener class to get coordinates
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            editLocation.setText("");
            progressBar.setVisibility(View.INVISIBLE);
            String latitude = "Latitude: " + location.getLatitude();
            String longitude = "Longitude: " + location.getLongitude();

            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() > 0)
                cityName = addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String s = latitude + "\n" + longitude + "\n\n Your current city is: " + cityName;
            editLocation.setText(s);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

}
