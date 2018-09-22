package com.example.albert.spyapp.core.events;

import java.util.LinkedList;

public class PhotosLinksLoadedEvent {

    public LinkedList<String> getLinks() {
        return links;
    }

    public LinkedList<String> links;

    public PhotosLinksLoadedEvent(LinkedList<String> list){
        this.links = list;
    }
}
