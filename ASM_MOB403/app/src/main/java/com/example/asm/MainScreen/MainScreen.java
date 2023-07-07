package com.example.asm.MainScreen;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.asm.MainScreen.ScreenFragment_BottomNav.BillFragment;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.HistoryFragment;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.HomeFragment;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Account_info;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Change_password;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Change_quests;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Change_username;
import com.example.asm.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainScreen extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
   Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
       toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

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
                        // Xử lý sự kiện khi nhấn Đăng xuất
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
