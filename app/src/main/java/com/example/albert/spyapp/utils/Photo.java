package com.example.albert.spyapp.utils;

public class Photo {

    private String image_title;
    private String url;

    public Photo(String url, String image_title) {
        this.image_title = image_title;
        this.url = url;
    }

    public Photo(String url) {
        this.url = url;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
