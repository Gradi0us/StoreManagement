package com.example.asm.Login_Register.API;

import com.example.asm.Login_Register.Model_User.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface login_api {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    login_api api = new Retrofit.Builder().baseUrl("https://testh0stmysql.000webhostapp.com/").addConverterFactory(GsonConverterFactory.create(gson)).build().create(login_api.class);


    @FormUrlEncoded
    @POST("API/api_login.php")
    Call<User> login_user(
                           @Field("username") String username,
                           @Field("password") String password
                           );
    @FormUrlEncoded
    @POST("API/api_getpass.php")
    Call<List<User>> getpassword(@Field("id") String id
    );

}
