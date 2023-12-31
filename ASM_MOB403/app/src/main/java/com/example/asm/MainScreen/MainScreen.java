package com.example.asm.MainScreen;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.asm.Login_Register.login;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.BillFragment;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.HistoryFragment;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.HomeFragment;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.getinfo_api;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Account_info;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Change_password;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Change_quests;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Change_username;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.UserProfile;
import com.example.asm.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreen extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public String userId;
    Toolbar toolbar;
    TextView txt_name;
    ImageView img_avatar;
    public String ìd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");

        checkinfo(userId);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        txt_name = findViewById(R.id.txt_name);
        img_avatar = findViewById(R.id.img_avatar);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Thiết lập nút menu cho ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        // Xử lý sự kiện khi nút menu được nhấn
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_item1:
                        selectedFragment = new Account_info();
                        break;
                    case R.id.nav_item2:
                        selectedFragment = new Change_username();
                        break;
                    case R.id.nav_item3:
                        selectedFragment = new Change_password();
                        break;
                    case R.id.nav_item4:
                        selectedFragment = new Change_quests();
                        break;
                    case R.id.nav_item5:
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear(); // Xoá tất cả dữ liệu lưu trữ
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);
                        Toast.makeText(MainScreen.this, "Đăng xuất", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        // Mặc định là HomeFragment
                        selectedFragment = new HomeFragment();
                        break;
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_bill:
                        selectedFragment = new BillFragment();
                        break;
                    case R.id.nav_history:
                        selectedFragment = new HistoryFragment();
                        break;
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                return true;
            }
        });

        // Mặc định hiển thị trang Home
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }

    private void checkinfo(String id) {
        getinfo_api.api.getInfoUser(id).enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                List<UserProfile> userProfileList = response.body();
                if (userProfileList != null && userProfileList.size() > 0) {
                    UserProfile userProfile = userProfileList.get(0);
                    String name = userProfile.getName();
                    String avatar = userProfile.getAvatar();

                    txt_name.setText(name);

                    Glide.with(MainScreen.this)
                            .load(avatar) // Link avatar
                            .placeholder(R.drawable.ic_history) // Ảnh placeholder hiển thị khi chờ tải ảnh
                            .error(R.drawable.ic_history) // Ảnh hiển thị khi lỗi tải ảnh
                            .into(img_avatar);
                    Toast.makeText(MainScreen.this, "profile is existed _Uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    // User profile data is not available, show the dialog to add new information.
                    showAddProductDialog();
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                // Handle failure if the API call fails.
                Toast.makeText(MainScreen.this, "Error fetching user profile data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



            private void showAddProductDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainScreen.this);
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.dialog_addprofile, null);
                builder.setTitle("Chưa có thông tin của Proflie hãy cập nhật ");
//                builder.setView(dialogView);


                builder.setPositiveButton("Thêm profle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Account_info accountInfoFragment = new Account_info();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, accountInfoFragment);
                        transaction.addToBackStack(null); // Để có thể quay lại fragment trước đó nếu cần
                        transaction.commit();

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }




public void navigationback(Fragment fragment){
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment_container, fragment);
    transaction.addToBackStack(null); // Để có thể quay lại fragment trước đó nếu cần
    transaction.commit();
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
