package com.example.asm.MainScreen.ScreenFragment_DrawerNav;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static android.service.controls.ControlsProviderService.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asm.MainScreen.MainScreen;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.addproduct_api;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.getinfo_api;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.API.upload_newinfo;
import com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model.UserProfile;
import com.example.asm.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Account_info extends Fragment {

TextView check,txt_name,txt_email,txt_phone;
String id;
ImageView edit_name,edit_email,edit_phone,imageavatar;
EditText edt_name,edt_email,edt_phone;
Button btn_save;
    private static final int REQUESTS_CODE_FOLDER = 123;
    private static final int RESULT_OK = -1;
    Bitmap imagess;
    FirebaseStorage storage;
    StorageReference storageRef;
    SharedPreferences sharedPreferences;
    private EditText edtName1, edtEmail1, edtPhone1;
    private Button btnSave,img;

    private UserProfile userProfile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_account_info, container, false);
//        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String userId = sharedPreferences.getString("userId", "");
//        Toast.makeText(getActivity(), userId+"abc", Toast.LENGTH_SHORT).show();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        MainScreen mainScreen = (MainScreen) getActivity();
        id = mainScreen.userId;
        anhxa(v);
        edit_Info();
        checkinfo(id);
        check.setText(mainScreen.userId);


        return v;
    }
    private void showAddProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_addprofile, null);
        builder.setView(dialogView);

        edtName1 = dialogView.findViewById(R.id.edittext_dialogname);
        edtEmail1 =  dialogView.findViewById(R.id.edittext_dialogemail);
        edtPhone1 =  dialogView.findViewById(R.id.edittext_dialogphone);
        img =  dialogView.findViewById(R.id.img_avatar);

        Map<String, Object> profileuser = new HashMap<>();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/UserProfile/*");
                startActivityForResult(intent, REQUESTS_CODE_FOLDER);
            }
        });

        builder.setPositiveButton("Thêm profle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    String name = edtName1.getText().toString().trim();
                    String email  = edtEmail1.getText().toString().trim();
                    int phone  =  Integer.parseInt(edtPhone1.getText().toString().trim());

                if (id.isEmpty() || name.isEmpty() || email.isEmpty() || phone <= 0 ) {
                    Toast.makeText(getActivity(), "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    profileuser.put("id", id);
                    profileuser.put("name", name);
                    profileuser.put("phone", phone);
                    profileuser.put("email", email);


                    if (imagess != null) {
                        StorageReference cvRef = storageRef.child("images/Userprofile/" + id);
                        ByteArrayOutputStream cvStream = new ByteArrayOutputStream();
                        imagess.compress(Bitmap.CompressFormat.JPEG, 100, cvStream);
                        byte[] cvBytes = cvStream.toByteArray();
                        UploadTask uploadTask = cvRef.putBytes(cvBytes);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                cvRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        profileuser.put("avatar", uri.toString());
                                        addUserprofile( profileuser);
                                    }
                                });
                            }
                        });
                    }
                }

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
    private void checkinfo(String id) {
        getinfo_api.api.getInfoUser(id).enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                List<UserProfile> userProfileList = response.body();
                if (userProfileList != null && userProfileList.size() > 0) {
                    UserProfile userProfile = userProfileList.get(0);
                    String name = userProfile.getName();
                    String email = userProfile.getEmail();
                    int phone = userProfile.getPhone();
                    String avatar = userProfile.getAvatar();

                        txt_name.setText(name);
                        txt_email.setText(email);
                    txt_phone.setText(String.valueOf(phone)); // Vì txt_phone là TextView, cần chuyển phone (kiểu int) thành String để setText.

                    // Sử dụng Glide để hiển thị ảnh từ link avatar vào ImageView holder.productimgview
                    Glide.with(getContext())
                            .load(avatar) // Link avatar
                            .placeholder(R.drawable.ic_history) // Ảnh placeholder hiển thị khi chờ tải ảnh
                            .error(R.drawable.ic_history) // Ảnh hiển thị khi lỗi tải ảnh
                            .into(imageavatar);
                    Toast.makeText(getActivity(), "profile is existed _Uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    // User profile data is not available, show the dialog to add new information.
                    showAddProductDialog();
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                // Handle failure if the API call fails.
                Toast.makeText(getActivity(), "Error fetching user profile data"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserprofile(Map<String, Object> profileuser) {
        String id = (String) profileuser.get("id");
        String name = (String) profileuser.get("name");
        String avatar = (String) profileuser.get("avatar");
        int phone = (int) profileuser.get("phone");
        String email = (String) profileuser.get("email");

        upload_newinfo.api.add_userInfo(id, name, avatar,phone, email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody>call, Response<ResponseBody>response) {
                if (response.isSuccessful()) {
                                            String jsonString = response.body().toString();
                    // Xử lý chuỗi JSON jsonString theo ý muốn
                    Toast.makeText(getActivity(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý khi không thành công
                    Toast.makeText(getActivity(), "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String errorMessage = "Lỗi: " + t.getMessage();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }
    private void edit_Info(){

        edt_name.setVisibility(View.GONE);
        edt_email.setVisibility(View.GONE);
        edt_phone.setVisibility(View.GONE);
        btn_save.setVisibility(View.GONE);
        edit_name.setOnClickListener(view -> {
            txt_name.setVisibility(View.GONE);
            edt_name.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.VISIBLE);
        });
        edit_email.setOnClickListener(view -> {
            txt_email.setVisibility(View.GONE);
            edt_email.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.VISIBLE);
        });
        edit_phone.setOnClickListener(view -> {
            txt_phone.setVisibility(View.GONE);
            edt_phone.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.VISIBLE);
        });
    }
    private void anhxa(View v){
        check = v.findViewById(R.id.id_account);
        edit_name = v.findViewById(R.id.edit_name);
        edit_email = v.findViewById(R.id.edit_email);
        edit_phone = v.findViewById(R.id.edit_phone);
        txt_name = v.findViewById(R.id.txt_name);
        txt_email = v.findViewById(R.id.txt_email);
        txt_phone = v.findViewById(R.id.txt_phone);
        edt_name = v.findViewById(R.id.edt_name);
        edt_email = v.findViewById(R.id.edt_email);
        edt_phone = v.findViewById(R.id.edt_phone);
        btn_save = v.findViewById(R.id.save);
        imageavatar = v.findViewById(R.id.img);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTS_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                imagess = BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}