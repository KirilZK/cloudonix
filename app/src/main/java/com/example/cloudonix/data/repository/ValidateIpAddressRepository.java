package com.example.cloudonix.data.repository;

import com.example.cloudonix.data.domain.IpAddressResponse;

public interface ValidateIpAddressRepository {

    interface IpAddressCallback{
       void onIpAddressResponse(IpAddressResponse ipAddress);
        void onError(String errorMsg);
    }

    void validateIpAddress(IpAddressCallback callback,String ipAddress);
}
