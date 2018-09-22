package com.example.albert.spyapp.requests.callbacks;


import com.example.albert.spyapp.core.eventbus.GlobalEventBusSingleton;
import com.example.albert.spyapp.core.events.StalkerAddedEvent;
import com.example.albert.spyapp.core.events.StalkerAddingErrorEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStalkerCallback implements Callback<String> {

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        GlobalEventBusSingleton.getInstance().getEventBus().post(new StalkerAddedEvent("Sign up successful"));
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        GlobalEventBusSingleton.getInstance().getEventBus().post(new StalkerAddingErrorEvent(t.getMessage()));
    }
}
