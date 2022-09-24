package com.pvting.dynamicproxy.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkService {
    @GET("/s")
    Call<Object> baiduSearch();
}
