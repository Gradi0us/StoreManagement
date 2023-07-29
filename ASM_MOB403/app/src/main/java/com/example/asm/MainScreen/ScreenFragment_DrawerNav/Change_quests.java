package com.example.asm.MainScreen.ScreenFragment_DrawerNav;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asm.MainScreen.MainScreen;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.getinfo_api;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.UserProfile;
import com.example.asm.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Change_quests extends Fragment {
MainScreen mainScreen;
String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_quests, container, false);
        mainScreen = (MainScreen) getActivity();
        id = mainScreen.userId;

        checkinfo(id);
        return v;
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



                } else {
                    // User profile data is not available, show the dialog to add new information.
                    showAddProductDialog();
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                // Handle failure if the API call fails.
                Toast.makeText(getActivity(), "Error fetching user profile data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void showAddProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.dialog_addprofile, null);
        builder.setTitle("Chưa có thông tin của Proflie hãy cập nhật ");
//                builder.setView(dialogView);


        builder.setPositiveButton("Thêm profle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Fragment accountInfoFragment = new Account_info();
                mainScreen.navigationback(accountInfoFragment);

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
}