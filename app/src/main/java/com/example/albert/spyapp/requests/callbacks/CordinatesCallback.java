package com.example.albert.spyapp.requests.callbacks;

import android.util.Log;

import com.example.albert.spyapp.cordinates.Cordinates;
import com.example.albert.spyapp.cordinates.LastLocationSingleton;
import com.example.albert.spyapp.requests.responseModels.CordsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CordinatesCallback implements Callback<CordsResponse> {

    @Override
    public void onResponse(Call<CordsResponse> call, Response<CordsResponse> response) {
        Cordinates cordinates = response.body().parseResponse();
        LastLocationSingleton.getInstance().setLastLocation(cordinates);
    }

    @Override
    public void onFailure(Call<CordsResponse> call, Throwable t) {
        Log.d("onFailure",t.getMessage()+" "+call.request().url().toString());
    }
}
