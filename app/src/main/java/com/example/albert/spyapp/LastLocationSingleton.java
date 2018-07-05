package com.example.albert.spyapp;

import android.location.Location;

public class LastLocationSingleton {

    static LastLocationSingleton lastLocationSingleton;

    public static LastLocationSingleton getLastLocationSingleton() {
        return lastLocationSingleton;
    }

    private Cordinates lastcordinates;

    private Cordinates getLastcordinates(){
        return lastcordinates;
    }

    LastLocationSingleton(){

    }

    public void setLastLocation(Cordinates location){
        lastcordinates=location;
    }
    synchronized static LastLocationSingleton getInstance(){

        if(lastLocationSingleton==null){
            lastLocationSingleton=new LastLocationSingleton();
        }

        return lastLocationSingleton;

    }

}
