package com.example.albert.spyapp.requests.responseModels;

import com.example.albert.spyapp.requests.retrofitmodels.LoginModel;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("")
    private String rawResponse;

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public LoginModel getLoginModel(){
        if(!rawResponse.equals("")){
            return new LoginModel(true);
        }
        return  new LoginModel(false);
    }
}
