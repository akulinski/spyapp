package com.example.albert.spyapp.requests.callbacks;

import android.util.Log;

import com.example.albert.spyapp.cordinates.Cordinates;
import com.example.albert.spyapp.cordinates.LastLocationSingleton;
import com.example.albert.spyapp.core.eventbus.GlobalEventBusSingleton;
import com.example.albert.spyapp.core.events.CordinatesEvent;
import com.example.albert.spyapp.requests.responseModels.CordsResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CordinatesCallback implements Callback<CordsResponse> {

    @Override
    public void onResponse(Call<CordsResponse> call, Response<CordsResponse> response) {

        Cordinates cordinates = response.body().parseResponse();

        GlobalEventBusSingleton.getInstance().getEventBus().post(new CordinatesEvent(cordinates));

        Log.d("CordinatesCallback",response.body().getCordinatesx()+" "+response.body().getCordinatesy());

    }

    @Override
    public void onFailure(Call<CordsResponse> call, Throwable t) {
        Log.d("onFailureCords",t.getMessage()+" "+call.request().url().toString());
    }
}
