package com.example.asm.Login_Register.API;

import com.example.asm.Login_Register.Model_User.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.http.FormUrlEncoded;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface register_api {
    //format định dạng cho thời gian
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    //buider retrofit để link api
    // link mặc định: https://testh0stmysql.000webhostapp.com/API/api_insert.php
    //base là phần mặc định nhé sau gọi đến api nào thì sẽ call đến thức qua get post ... và đường dẫn

    register_api api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(register_api.class);
// truyền Model mà gson trả về sau khi chuyển từ json sang Object
@FormUrlEncoded
@POST("API/api_register.php")
Call<User> insert_user(  @Field("id") String id,
                          @Field("username") String username,
                          @Field("password") String password,
                          @Field("quests") String quests);



}
