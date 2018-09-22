package com.example.albert.spyapp.requests;

import com.example.albert.spyapp.requests.responseModels.TestResponse;
import com.example.albert.spyapp.requests.retrofitmodels.TestModel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

public class ApiClientTest {

    ApiInterface apiService;
    @Before
    public void setUp() throws Exception {

        apiService  = ApiClient.getClient().create(ApiInterface.class);
    }

    @Test
    public void getClient() {

        Call<TestResponse> call = apiService.getTest();
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                int statusCode = response.code();
                TestModel results = response.body().getSuccess();
                assertEquals(results.getSuccess(),"true");
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                // Log error here since request failed
            }
        });
        call.request();

    }
}