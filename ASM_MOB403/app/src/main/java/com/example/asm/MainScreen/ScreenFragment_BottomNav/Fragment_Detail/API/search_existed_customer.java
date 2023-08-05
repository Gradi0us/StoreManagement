package com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.API;

import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model.Customer;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.getinfo_api;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface search_existed_customer {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    search_existed_customer api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(search_existed_customer.class);

    @FormUrlEncoded
    @POST("API/api_customerfind.php")
    Call<List<Customer>> getcustomerinfo(
            @Field("customerphone") int customerphone
    );

    @FormUrlEncoded
    @POST("API/api_findcustomerwithnameandphone.php")
    Call<List<Customer>> getcustomerinfowithphoneandusername(
            @Field("customername") String customername,
            @Field("customerphone") int customerphone

    );

}
