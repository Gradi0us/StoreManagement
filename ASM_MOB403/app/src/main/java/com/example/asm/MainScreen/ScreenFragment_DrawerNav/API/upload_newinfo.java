package com.example.asm.MainScreen.ScreenFragment_DrawerNav.API;

import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.UserProfile;
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

public interface upload_newinfo {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    upload_newinfo api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(upload_newinfo.class);

    @FormUrlEncoded
    @POST("API/api_addnewprofileUser.php")
    Call<ResponseBody> add_userInfo(
            @Field("id") String id,
            @Field("name") String name,
            @Field("avatar") String avatar,
            @Field("phone") int phone,
            @Field("email") String email

    );
}



