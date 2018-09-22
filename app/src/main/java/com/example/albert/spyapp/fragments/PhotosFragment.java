package com.example.albert.spyapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.albert.spyapp.R;
import com.example.albert.spyapp.core.eventbus.GlobalEventBusSingleton;
import com.example.albert.spyapp.core.events.PhotosLinksLoadedEvent;
import com.example.albert.spyapp.requests.ApiClient;
import com.example.albert.spyapp.requests.ApiInterface;
import com.example.albert.spyapp.requests.callbacks.LinksCallback;
import com.example.albert.spyapp.utils.Permission;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.LinkedList;

import javax.annotation.Nullable;

import retrofit2.Call;

public class PhotosFragment extends Fragment {
    private Button prev;
    private Button next;
    private ImageView photo;
    private Permission permission;
    private int index;
    private String stalker;
    private static LinkedList<String> photosLinks;
    private RequestQueue queue;
    private EventBus eventBus;
    private ApiInterface apiService;


    public void subscribeToEventBus() {
        eventBus = GlobalEventBusSingleton.getInstance().getEventBus();
        eventBus.register(new PhotosLinksLoadedEventHandler());
    }

    @SuppressLint("StaticFieldLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_activity, parent, false);
        photosLinks = new LinkedList<>();
        subscribeToEventBus();
        this.stalker = "tomeczek";
        queue = Volley.newRequestQueue(this.getContext());
        index = 0;
        super.onCreate(savedInstanceState);
        prev = (Button) rootView.findViewById(R.id.prevButton);
        next = (Button) rootView.findViewById(R.id.nextButton);
        photo = (ImageView) rootView.findViewById(R.id.picture);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        LinksCallback linksCallback = new LinksCallback();

        Call<String> call = apiService.getLinks(stalker);
        call.enqueue(linksCallback);


//        permission = new Permission(this, this);
//        if (!permission.checkPermissions()) permission.request();
//        System.out.println("--------"+links.getSize()+"--------");

        return rootView;
    }

    private final class PhotosLinksLoadedEventHandler {

        @Subscribe
        public void handleEvent(PhotosLinksLoadedEvent event) {

            PhotosFragment.photosLinks = event.links;
        }
    }

}
