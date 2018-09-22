package com.example.albert.spyapp.requests.callbacks;

import com.example.albert.spyapp.requests.responseModels.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestCallback implements Callback<TestResponse> {

    private boolean connected = false;

    @Override
    public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
        String results = response.body().getSuccess();
        if(results.equals("true")){
            connected = true;
        }else{
            connected = false;
        }
    }

    @Override
    public void onFailure(Call<TestResponse> call, Throwable t) {
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }
}
