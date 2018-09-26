package com.example.albert.spyapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.albert.spyapp.requests.ApiClient;
import com.example.albert.spyapp.requests.ApiInterface;
import com.example.albert.spyapp.requests.callbacks.CordinatesCallback;
import com.example.albert.spyapp.requests.responseModels.CordsResponse;

import retrofit2.Call;

public class GetLocationFromServerService extends IntentService {

    private ApiInterface apiService;

    public GetLocationFromServerService() {
        super(GetLocationFromServerService.class.getSimpleName());
        apiService = ApiClient.getClient().create(ApiInterface.class);
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

    public void getLocFromServer() {

        new Thread(() -> {
            while (true) {

                CordinatesCallback cordinatesCallback = new CordinatesCallback();

                Call<CordsResponse> call = apiService.getCords();

                call.enqueue(cordinatesCallback);
                Log.d("workingService","working");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
