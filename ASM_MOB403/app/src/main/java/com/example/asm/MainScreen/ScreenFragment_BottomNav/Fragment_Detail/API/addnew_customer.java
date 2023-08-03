package com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.API;

import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface addnew_customer {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    addnew_customer api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(addnew_customer .class);


    @FormUrlEncoded
    @POST("API/api_addnewcustomer.php")
    Call<Customer> addnewcustomer(
            @Field("customerphone") int customerphone,
            @Field("customername") String customername,
            @Field("id") String id
    );

}
