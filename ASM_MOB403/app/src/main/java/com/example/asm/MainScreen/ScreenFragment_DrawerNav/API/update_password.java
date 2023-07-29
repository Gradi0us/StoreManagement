package com.example.asm.MainScreen.ScreenFragment_DrawerNav.API;

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

public interface update_password {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    update_password api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(update_password.class);

    @FormUrlEncoded
    @POST("API/api_changepass.php")
    Call<List<User>> changepass(
            @Field("id") String id,
            @Field("password") String password

    );
}
