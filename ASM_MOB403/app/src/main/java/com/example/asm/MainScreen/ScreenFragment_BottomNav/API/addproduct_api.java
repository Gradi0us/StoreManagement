package com.example.asm.MainScreen.ScreenFragment_BottomNav.API;

import com.example.asm.Login_Register.API.login_api;
import com.example.asm.Login_Register.Model_User.User;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface addproduct_api {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    addproduct_api api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(addproduct_api.class);

    @FormUrlEncoded
    @POST("API/api_addProducts.php")
    Call<ResponseBody> add_products(
            @Field("id") String id,
            @Field("productname") String productname,
            @Field("producttype") String producttype,
            @Field("productimage") String productimage,
            @Field("price") double price,
            @Field("size") String size,
            @Field("mount") int mount
    );


}

