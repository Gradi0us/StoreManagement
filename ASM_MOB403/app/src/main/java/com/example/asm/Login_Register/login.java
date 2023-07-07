package com.example.asm.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.Login_Register.API.login_api;
import com.example.asm.Login_Register.Model_User.User;
import com.example.asm.MainScreen.MainScreen;
import com.example.asm.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các thành phần trong file XML
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(login.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        }else{
            login(username,password);
        }
    }
});

    }
    private void login(String username,String password){
        login_api.api.login_user(username,password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    if(user != null){
                        String id = user.getId();
                        // Lưu trữ id vào SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId", id);
                        editor.apply();
                        Intent intent = new Intent(getApplication(), MainScreen.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String errorMessage = "Lỗi login: " + t.getMessage();
                Toast.makeText(login.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
