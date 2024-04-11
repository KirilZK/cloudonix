package com.example.cloudonix.data;

import com.example.cloudonix.data.repository.ValidateIpAddressRepository;
import com.example.cloudonix.data.repository.ValidateIpAddressRepositoryImpl;

public class DataManager {
    private static DataManager sInstance;

    private DataManager() {

    }

    public static synchronized DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }



    public ValidateIpAddressRepository getRepository() {
        ApiInterface api = ApiClient.getInstance().getApi();
        return new ValidateIpAddressRepositoryImpl(api);

    }

}
