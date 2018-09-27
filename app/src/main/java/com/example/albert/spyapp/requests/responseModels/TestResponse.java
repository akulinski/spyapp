package com.example.albert.spyapp.requests.responseModels;

import com.example.albert.spyapp.requests.retrofitmodels.TestModel;
import com.google.gson.annotations.SerializedName;

public class TestResponse {


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public TestResponse(String success) {
        this.success = success;
    }

    @SerializedName("success")
    private String success;

}
