package com.example.albert.spyapp.core.events;

import com.example.albert.spyapp.cordinates.Cordinates;

public class CordinatesEvent {

    private Cordinates cordinates;

    public Cordinates getCordinates() {
        return cordinates;
    }

    public void setCordinates(Cordinates cordinates) {
        this.cordinates = cordinates;
    }

    public CordinatesEvent(Cordinates cordinates) {
        this.cordinates = cordinates;
    }
}
