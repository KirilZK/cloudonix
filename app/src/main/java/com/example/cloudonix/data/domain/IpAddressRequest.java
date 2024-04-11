package com.example.cloudonix.data.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IpAddressRequest {

    @Expose
    @SerializedName("address")
    public String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
