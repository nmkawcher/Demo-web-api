package com.kawcher.demoapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface ApiResponse {

   @GET("photos")
    Call<List<ApiModel>>getResponse();
}
