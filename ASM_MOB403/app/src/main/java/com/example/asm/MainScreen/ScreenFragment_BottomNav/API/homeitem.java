package com.example.asm.MainScreen.ScreenFragment_BottomNav.API;

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
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface homeitem {

    @GET("API/api_homeitem.php")
    Call<List<Products>> getProducts();

}
