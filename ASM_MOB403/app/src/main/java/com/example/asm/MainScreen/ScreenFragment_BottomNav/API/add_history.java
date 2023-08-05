package com.example.asm.MainScreen.ScreenFragment_BottomNav.API;

import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.History;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_history {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    add_history api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(add_history.class);

    @FormUrlEncoded
    @POST("API/api_addhistory.php")
    Call<List<History>> addnewhistory(
            @Field("id") String id,
            @Field("uid") String uid,
            @Field("pid") String pid,
            @Field("date")String date,
            @Field("customerid")String customerid,
            @Field("mountbuy")int mountbuy,
            @Field("price")int price

    );
    @FormUrlEncoded
    @POST("API/api_gethistorybyuid.php")
    Call<List<History>> gethistorybyuid(
            @Field("uid") String uid


    );
}
