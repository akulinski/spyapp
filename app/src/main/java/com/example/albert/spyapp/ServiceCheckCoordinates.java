package com.example.albert.spyapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class ServiceCheckCoordinates extends IntentService{
    ServiceCheckCoordinates() {
        super(ServiceCheckCoordinates.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("getCoor","coor");
        getCoordinates();
        this.stopSelf();
    }


    private void sendBroadcastMessageCoordinates(String coordinatex, String coordinatey) {
        Log.d("SendingBroadCast","start");
        Intent intent = new Intent();
        intent.setAction("com.example.albert.spyapp.gps;");
        System.out.println("putting x "+coordinatex);
        intent.putExtra("coordinatesx", coordinatex);
        intent.putExtra("coordinatesy", coordinatey);
        sendBroadcast(intent);
        Log.d("EndingBroadcast", "end");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }


    public void getCoordinates() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Cordinates cordinates = LastLocationSingleton.getInstance().getLastcordinates();
                    System.out.println("Singleton "+cordinates.cordinatesx);
                    sendBroadcastMessageCoordinates(cordinates.cordinatesx, cordinates.cordinatesy);
                    Log.d("ELOELO2", "jojorea");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
