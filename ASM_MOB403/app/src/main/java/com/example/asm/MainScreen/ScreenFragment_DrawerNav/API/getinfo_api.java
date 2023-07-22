package com.example.asm.MainScreen.ScreenFragment_DrawerNav.API;

import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface getinfo_api {

        Gson gson = new GsonBuilder().setLenient().create();

        getinfo_api api = new Retrofit.Builder()
                .baseUrl("https://testh0stmysql.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(getinfo_api.class);

        @FormUrlEncoded
        @POST("API/api_getprofileUser.php")
        Call<List<UserProfile>> getInfoUser(
                @Field("id") String id
        );

    }



