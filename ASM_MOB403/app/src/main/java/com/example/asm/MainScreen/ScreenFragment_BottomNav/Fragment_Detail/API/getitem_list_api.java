package com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.API;

import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;
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

public interface getitem_list_api {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    getitem_list_api api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(getitem_list_api.class);
@FormUrlEncoded
    @POST("API/api_getitemid.php")
    Call<Products> getitemproductid(
            @Field("id") String id
    );
}
