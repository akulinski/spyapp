package com.example.albert.spyapp.cordinates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cordinates {

    @Expose
    @SerializedName("cordinatesx")
    private String cordinatesx;

    public void setCordinatesx(String cordinatesx) {
        this.cordinatesx = cordinatesx;
    }

    public void setCordinatesy(String cordinatesy) {
        this.cordinatesy = cordinatesy;
    }

    @Expose
    @SerializedName("cordinatesy")
    private String cordinatesy;

    public Cordinates(String cordinatesx, String cordinatesy){
        this.cordinatesx=cordinatesx;
        this.cordinatesy=cordinatesy;
    }


    public String getX( )
    {
        return cordinatesx;
    }

    public String getY( )
    {
        return cordinatesy;
    }

    @Override
    public String toString() {
        return cordinatesx +" "+cordinatesy;
    }
}
