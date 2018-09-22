package com.example.albert.spyapp.requests.retrofitmodels;

import com.google.gson.annotations.SerializedName;

public class TestModel {

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public TestModel(String success) {
        this.success = success;
    }

    @SerializedName("success")
    private String success;
}
