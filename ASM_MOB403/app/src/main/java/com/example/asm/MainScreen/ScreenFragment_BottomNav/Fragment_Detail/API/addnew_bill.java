package com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.API;

import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model.Bill;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.type.DateTime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface addnew_bill {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    addnew_bill api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(addnew_bill.class);

    @FormUrlEncoded
    @POST("API/api_addbill.php")
    Call<Bill> addnewbill(
            @Field("id") String id,
            @Field("uid") String uid,
            @Field("pid") String pid,
            @Field("customerid") String customerid,
            @Field("date")String date,
            @Field("totalcost") int totalcost,
            @Field("status") String status


            );
}
