package com.example.albert.spyapp;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import java.util.ArrayList;


public class Permission {
    private ArrayList<String> permissions;
    private Activity activity;

    public Permission(Activity activity) {
        this.activity = activity;
        permissions = new ArrayList<String>() {{
            add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            add(android.Manifest.permission.CAMERA);
        }};
    }

    public void request() {
        ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), 1);
    }

}
