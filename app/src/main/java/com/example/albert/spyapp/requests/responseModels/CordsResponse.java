package com.example.albert.spyapp.requests.responseModels;

import com.example.albert.spyapp.cordinates.Cordinates;
import com.example.albert.spyapp.cordinates.LastLocationSingleton;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CordsResponse {

    @Expose
    @SerializedName("cordinatesx")
    private String cordinatesx;


    @Expose
    @SerializedName("cordinatesy")
    private String cordinatesy;

    public void setCordinatesx(String cordinatesx) {
        this.cordinatesx = cordinatesx;
    }

    public void setCordinatesy(String cordinatesy) {
        this.cordinatesy = cordinatesy;
    }

    public String getCordinatesx() {
        return cordinatesx;
    }

    public String getCordinatesy() {
        return cordinatesy;
    }

    CordsResponse(){

    }

    public Cordinates parseResponse(){

        return new Cordinates(this.cordinatesx,this.cordinatesy);
    }

}
