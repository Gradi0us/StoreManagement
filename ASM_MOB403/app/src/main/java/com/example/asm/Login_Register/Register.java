package com.example.asm.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm.Login_Register.API.register_api;
import com.example.asm.Login_Register.Model_User.User;
import com.example.asm.R;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextQuests;
    private Button buttonRegister,buttonlogjn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ các thành phần từ mã XML
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextQuests = findViewById(R.id.editTextQuests);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonlogjn = findViewById(R.id.buttontologin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String quests = editTextQuests.getText().toString();
                String id = generateID();
//                String id = "1231231";
                if (username.isEmpty() || password.isEmpty() || quests.isEmpty()) {
                    Toast.makeText(Register.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(id, username, password, quests);
                }
            }
        });
        buttonlogjn.setOnClickListener(view -> {
            Intent intent  = new Intent(getApplication(),login.class);
            startActivity(intent);
        });
    }

    public static String generateID() {
        // Tạo chuỗi ID từ thời gian hiện tại và 3 số ngẫu nhiên
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomNumber = generateRandomNumber();

        return timestamp + randomNumber;
    }

    private static String generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(90) + 10;

        return String.valueOf(randomNumber);
    }

    private void registerUser(String id, String username, String password, String quests) {

        register_api.api.insert_user(id, username, password, quests).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(getApplication(),login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Register.this, "Lỗi đăng ký 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Lỗi đăng ký 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String errorMessage = "Lỗi đăng ký: " + t.getMessage();
                Toast.makeText(Register.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
