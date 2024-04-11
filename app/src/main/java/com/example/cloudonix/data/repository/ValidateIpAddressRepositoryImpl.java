package com.example.cloudonix.data.repository;

import com.example.cloudonix.data.ApiInterface;
import com.example.cloudonix.data.domain.IpAddressRequest;
import com.example.cloudonix.data.domain.IpAddressResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateIpAddressRepositoryImpl implements ValidateIpAddressRepository {

    private final ApiInterface restClient;


    public ValidateIpAddressRepositoryImpl(ApiInterface restClient) {
        this.restClient = restClient;


    }


    @Override
    public void validateIpAddress(IpAddressCallback callback, String ipAddress) {


        IpAddressRequest request = new IpAddressRequest();
        request.setAddress(ipAddress);

        Call<IpAddressResponse> call = restClient.validateIpAddress(request);
        call.enqueue(new Callback<IpAddressResponse>() {
            @Override
            public void onResponse(Call<IpAddressResponse> call, Response<IpAddressResponse> response) {
                if (!response.isSuccessful()) {
                    callback.onError(response.message());
                }
                IpAddressResponse data = response.body();
                if(data != null){
                    data.setAddress(ipAddress);
                    callback.onIpAddressResponse(data);
                }

            }

            @Override
            public void onFailure(Call<IpAddressResponse> call, Throwable throwable) {
                callback.onError(throwable.getMessage());

            }
        });


    }
}

