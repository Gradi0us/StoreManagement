package com.example.asm.MainScreen.ScreenFragment_DrawerNav.API;

import com.example.asm.Login_Register.Model_User.User;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.CodeAuthenticate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface getpass_code_api {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    getpass_code_api api = new Retrofit.Builder()
            .baseUrl("https://testh0stmysql.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(getpass_code_api.class);

    @FormUrlEncoded
    @POST("API/api_getpassword.php")
    Call<List<User>> getpass(
            @Field("id") String id

    );
    @FormUrlEncoded
    @POST("API/api_checkconfirm.php")
    Call<List<CodeAuthenticate>> verify_code(
            @Field("id") String id

    );
}
