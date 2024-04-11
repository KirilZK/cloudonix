package com.example.cloudonix.data;

import com.example.cloudonix.data.domain.IpAddressRequest;
import com.example.cloudonix.data.domain.IpAddressResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Content-Type: application/json"})
    @POST("/")
    Call<IpAddressResponse> validateIpAddress(@Body IpAddressRequest ipAddress);
}
