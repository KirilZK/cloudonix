package com.example.cloudonix.data;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String URL = "https://s7om3fdgbt7lcvqdnxitjmtiim0uczux.lambda-url.us-east-2.on.aws";

    private ApiInterface api;

    private static ApiClient singleton;

    private ApiClient() {

        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(URL).build();

       api = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getInstance() {
        if (singleton == null) {
            synchronized (ApiClient.class) {
                if (singleton == null) {
                    singleton = new ApiClient();
                }
            }
        }
        return singleton;
    }

    public ApiInterface getApi() {
        return api;
    }
}
