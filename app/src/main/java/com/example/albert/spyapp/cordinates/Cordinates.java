package com.example.albert.spyapp.cordinates;

public class Cordinates {
    private String cordinatesx;
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
