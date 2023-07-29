package com.example.asm.MainScreen.ScreenFragment_DrawerNav;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asm.Login_Register.Model_User.User;
import com.example.asm.MainScreen.MainScreen;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.emailsend_api;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.getinfo_api;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.getpass_code_api;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.update_password;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.CodeAuthenticate;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.UserProfile;
import com.example.asm.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Handler;
public class Change_password extends Fragment {

    EditText currentpass, newpass, verifycode;
    Button changepass, getcode;
    TextView text, userid,passcu,codetuemail;
    String id;
    LinearLayout layout;
    private String latestVerificationCode = "";
    String authencode, code, oldpass, password, authcode, passwords;
    private CountDownTimer countDownTimer;
    MainScreen mainScreen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        mainScreen = (MainScreen) getActivity();
        id = mainScreen.userId;
        anhxa(v);
        checkinfo(id);
        setVisiblely();

        userid.setText(id);

        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendcode(id);
                startCountdownTimer();
            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getdata(id);
                validate(id);

            }
        });
        return v;
    }

    private void anhxa(View v) {
        currentpass = v.findViewById(R.id.currentpass);
        newpass = v.findViewById(R.id.newpass);
        verifycode = v.findViewById(R.id.confirm_code);
        changepass = v.findViewById(R.id.changepass);
        getcode = v.findViewById(R.id.getcode);
        text = v.findViewById(R.id.confirm);
        passcu = v.findViewById(R.id.passcu);
        codetuemail = v.findViewById(R.id.codetuemail);
        userid = v.findViewById(R.id.userid);
        layout = v.findViewById(R.id.layoutgetcode);
    }

    private void setVisiblely() {

    }

    private void sendcode(String id) {
        emailsend_api.api.sendemail(id).enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_Response", "Response: " + response.body());
                    Toast.makeText(getActivity(), "gửi thành công", Toast.LENGTH_SHORT).show();
                    // Get the verification code from the API response
                    if (response.body() != null && !response.body().isEmpty()) {

                    }
                } else {
                    Log.d("API_Response", "Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
            }
        });
    }


    private void startCountdownTimer() {
        getcode.setEnabled(false); // Disable the button during the countdown

        countDownTimer = new CountDownTimer(60000, 1000) { // 60 seconds with a tick every 1 second
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                getcode.setText("Resend in " + seconds + "s");
            }

            public void onFinish() {
                getcode.setEnabled(true); // Enable the button when the countdown finishes
                getcode.setText("Get Code");
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


// ...
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

    private void validate(String id) {
        authcode = verifycode.getText().toString().trim();
        code = verifycode.getText().toString().trim();
        oldpass = currentpass.getText().toString().trim();
        password = newpass.getText().toString().trim();

        getpass_code_api.api.getpass(id).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                if (users != null && users.size() > 0) {
                    User user = users.get(0);
                    passwords = user.getPassword();
                    passcu.setText(passwords);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        getpass_code_api.api.verify_code(id).enqueue(new Callback<List<CodeAuthenticate>>() {
            @Override
            public void onResponse(Call<List<CodeAuthenticate>> call, Response<List<CodeAuthenticate>> response) {
                List<CodeAuthenticate> codeAuthenticates = response.body();
                if (codeAuthenticates != null && codeAuthenticates.size() > 0) {
                    CodeAuthenticate codeAuthenticate = codeAuthenticates.get(0);
                    authencode = codeAuthenticate.getCode();
                    codetuemail.setText(authencode);

                }
            }

            @Override
            public void onFailure(Call<List<CodeAuthenticate>> call, Throwable t) {

            }
        });

        // Introduce a 3-second delay before comparing data
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                compareData();
            }
        }, 3000);
    }

    private void compareData() {
        if (oldpass.equals(passwords)) {
            if (authcode.equals(authencode)) {
                changepass(id, password);
            } else {
                Toast.makeText(getActivity(), "Mã xác nhận không đúng", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Mật khẩu không đúng với mật khẩu cũ", Toast.LENGTH_SHORT).show();
        }
    }

    private void changepass(String id, String password) {
        Toast.makeText(getActivity(), "changeeeeeeeeeeepassss", Toast.LENGTH_SHORT).show();
        update_password.api.changepass(id, password).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> userList = response.body();
                    if (userList != null && !userList.isEmpty()) {
                        User user = userList.get(0);
                        // Password updated successfully, handle the response or show a success message
                        Toast.makeText(getActivity(), "Password updated successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the case when the response is empty or contains no User objects
                        Toast.makeText(getActivity(), "Failed to update password.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case when the API call is not successful
                    Toast.makeText(getActivity(), "Failed to update password.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Handle API call failure
                Toast.makeText(getActivity(), "Failed to update password: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//private void getdata(String id){
//    Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();
//    getpass_code_api.api.getpass(id).enqueue(new Callback<List<User>>() {
//        @Override
//        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//            List<User> users = response.body();
//            if (users != null && users.size() > 0) {
//                User user = users.get(0);
//                passwords = user.getPassword();
//                passcu.setText(passwords);
//
//            }
//        }
//
//        @Override
//        public void onFailure(Call<List<User>> call, Throwable t) {
//
//        }
//    });
//
//
//    emailsend_api.api.verify_code(id).enqueue(new Callback<List<CodeAuthenticate>>() {
//        @Override
//        public void onResponse(Call<List<CodeAuthenticate>> call, Response<List<CodeAuthenticate>> response) {
//            List<CodeAuthenticate> codeAuthenticates = response.body();
//            if (codeAuthenticates != null && codeAuthenticates.size() > 0) {
//                CodeAuthenticate codeAuthenticate = codeAuthenticates.get(0);
//                authencode = codeAuthenticate.getCode();
//                codetuemail.setText(authencode);
//                Toast.makeText(getActivity(), ""+authencode, Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<List<CodeAuthenticate>> call, Throwable t) {
//
//        }
//    });
//
//}
}
