package com.example.albert.spyapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class ServiceCheckCoordinates extends IntentService{
    ServiceCheckCoordinates() {
        super(ServiceCheckCoordinates.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        getCoordinates();
        this.stopSelf();
    }


    private void sendBroadcastMessageCoordinates(String coordinatex, String coordinatey) {
        Intent intent = new Intent();
        intent.setAction("com.example.albert.spyapp.gps;");
        intent.putExtra("coordinatesx", coordinatex);
        intent.putExtra("coordinatesy", coordinatey);
        sendBroadcast(intent);
    }


    public void getCoordinates() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Cordinates cordinates = LastLocationSingleton.getInstance().getLastcordinates();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendBroadcastMessageCoordinates(cordinates.cordinatesx, cordinates.cordinatesy);
                }
            }
        }).start();
    }

}
