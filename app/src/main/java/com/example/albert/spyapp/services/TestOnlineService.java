package com.example.albert.spyapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.albert.spyapp.requests.ApiClient;
import com.example.albert.spyapp.requests.ApiInterface;
import com.example.albert.spyapp.requests.callbacks.TestCallback;
import com.example.albert.spyapp.requests.responseModels.TestResponse;
import com.example.albert.spyapp.utils.Urls;

import java.util.concurrent.locks.ReentrantLock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestOnlineService extends IntentService {

    private ReentrantLock lock = new ReentrantLock();
    private ApiInterface apiService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public TestOnlineService() {
        super(TestOnlineService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }

    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        testConnection();
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }


    private void sendBroadcastMessage(boolean connected) {
        try {
            lock.lock();
            Intent intent = new Intent();
            intent.setAction("com.example.albert.spyapp;");

            intent.putExtra("connected", connected);

            sendBroadcast(intent);
        } catch (Exception ex) {
//            Log.d("exception while sending","exec");
        } finally {
            lock.unlock();
        }


    }

    public void testConnection() {

        new Thread(() -> {

            while (true) {
                TestCallback testCallback = new TestCallback();

                try {

                    Call<TestResponse> call = apiService.getTest();
                    call.enqueue(testCallback);
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.getCause();
                } finally {
                    sendBroadcastMessage(testCallback.isConnected());
                }
            }

        }).start();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> sendBroadcastMessage(false));

    }
}
