package com.example.asm.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.asm.Login_Register.API.login_api;
import com.example.asm.Login_Register.Model_User.User;
import com.example.asm.MainScreen.MainScreen;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.getinfo_api;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.UserProfile;
import com.example.asm.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvScreenName;
    public String userId;
    ImageView imgacc;
    Toolbar toolbar;
    String nameacc,imglink,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        anhxa();
        etUsername.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);
        if (userId != null) {
            etUsername.setVisibility(View.GONE);
            getcurrentuser(userId);
        }
    btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override

    public void onClick(View view) {
        if (userId != null) {
            getpass(userId);
        }
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(login.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                login(username, password);
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
    private void getcurrentuser(String id){
        getinfo_api.api.getInfoUser(id).enqueue(new Callback<List<UserProfile>>() {
            @Override

            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                if (response.isSuccessful()){
                    List<UserProfile> userProfiles = response.body();
                    if (userProfiles != null && !userProfiles.isEmpty()) {
                        UserProfile userProfile = userProfiles.get(0);
                        nameacc = userProfile.getName();
                        imglink = userProfile.getAvatar();
                        Glide.with(login.this)
                                .load(imglink)
                                .placeholder(R.drawable.ic_history)
                                .error(R.drawable.ic_history)
                                .into(imgacc);
                        tvScreenName.setText(nameacc);
                    } else {
                        etUsername.setVisibility(View.VISIBLE);
                        etPassword.setVisibility(View.VISIBLE);
                        Toast.makeText(login.this, "User profile not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }



            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {

            }
        });
    }
    private void anhxa(){
        tvScreenName = findViewById(R.id.name_currentacc);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        imgacc  = findViewById(R.id.accimg);
    }
    private void getpass(String id){
        String password = etPassword.getText().toString().trim();
        login_api.api.getpassword(id).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    List<User> userList = response.body();
                    if (userList != null && !userList.isEmpty()) {
                        User user = userList.get(0);
                        String retrievedPassword = user.getPassword();
                        if (password.equalsIgnoreCase(retrievedPassword)) {
                            Intent intent = new Intent(login.this, MainScreen.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(login.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
