package com.example.albert.spyapp.requests.callbacks;

import android.util.Log;

import com.example.albert.spyapp.core.eventbus.GlobalEventBusSingleton;
import com.example.albert.spyapp.core.events.PhotosLinksLoadedEvent;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LinksCallback implements Callback<String> {

    public LinkedList<String> getPhotosLinks() {
        return photosLinks;
    }

    private LinkedList<String> photosLinks;

    public LinksCallback(){
        photosLinks = new LinkedList<>();
    }
    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        Log.d("links",response.body());
        String res = response.body().substring(1,response.body().length()-2);

        String[] parts = res.split("\\^");

        for(int i=0;i<parts.length;i++){
            parts[i] = parts[i].replace('_','/');
            photosLinks.add(parts[i]);
            Log.d("link",parts[i]);
            System.out.println("----------");
            System.out.println(parts[i]);
            System.out.println("----------");
        }

        GlobalEventBusSingleton.getInstance().getEventBus().post(new PhotosLinksLoadedEvent(photosLinks));
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }
}
