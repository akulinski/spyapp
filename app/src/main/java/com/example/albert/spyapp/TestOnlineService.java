package com.example.albert.spyapp;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

public class TestOnlineService extends IntentService {
    boolean connected =false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    TestOnlineService(){
        super(TestOnlineService.class.getSimpleName());
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }

    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        testConnection();
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    private void sendBroadcastMessage(boolean connected) {
            Intent intent = new Intent();
            intent.setAction("com.example.albert.spyapp;");

            intent.putExtra("connected", connected);

            sendBroadcast(intent);

    }

    public void testConnection() {
        final String url = Urls.TEST.url;

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    Log.d("While","started");
                    try
                    {
                        ServerRequest serverRequest=new ServerRequest(Urls.TEST.url);
                        serverRequest.makeTestRequest();
                        Log.d("serverRequest",serverRequest.getReturnedValue());
                        if (serverRequest.getReturnedValue().equals("\"{\\\"Success\\\":\\\"true\\\"}\""))
                        {
                            connected=true;
                            Log.d("connected?","true");
                            Log.d("Sending Broadcast","true");
                            sendBroadcastMessage(connected);
                        }
                        else
                        {
                            connected=false;
                            Log.d("connected?","false");
                            sendBroadcastMessage(connected);
                        }
                    }
                    catch (SecurityException s)
                    {
                        s.getCause();
                        Log.d("Securityexception",s.getMessage());
                    }
                    catch (Exception ex)
                    {
                        Log.d("exception",ex.getLocalizedMessage());
                    }

                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
