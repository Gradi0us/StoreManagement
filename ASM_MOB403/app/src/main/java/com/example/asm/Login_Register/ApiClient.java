package com.example.asm.Login_Register;

import com.example.asm.Login_Register.API.register_api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://testh0stmysql.000webhostapp.com/API/";

    private static Retrofit retrofit;

    public static register_api getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(register_api.class);
    }
}
