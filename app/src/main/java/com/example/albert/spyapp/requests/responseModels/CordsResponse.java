package com.example.albert.spyapp.requests.responseModels;

import com.example.albert.spyapp.cordinates.Cordinates;
import com.example.albert.spyapp.cordinates.LastLocationSingleton;
import com.google.gson.annotations.SerializedName;

public class CordsResponse {

    @SerializedName("")
    private String rawResponse;


    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public Cordinates parseResponse() {
        String[] parts = rawResponse.split(" ");
        String cordinatesx = parts[0];
        String cordinatesy = parts[1];
        Cordinates cordinates = new Cordinates(cordinatesx, cordinatesy);

        return  cordinates;
    }
}
