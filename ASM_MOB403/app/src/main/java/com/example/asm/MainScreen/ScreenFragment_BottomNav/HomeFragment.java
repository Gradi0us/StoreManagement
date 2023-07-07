package com.example.asm.MainScreen.ScreenFragment_BottomNav;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asm.Login_Register.Register;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.addproduct_api;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;
import com.example.asm.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    int REQUESTS_CODE_FOLDER = 123;
    int RESULT_OK = 123;
    Bitmap productimage;
    FirebaseStorage storage;
    StorageReference storageRef;
    String productImage = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userId = sharedPreferences.getString("userId", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton floatingActionButton = v.findViewById(R.id.addproduct);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProductDialog();
            }
        });
        return v;
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
    private void showAddProductDialog() {
        // Tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_product, null);
        builder.setView(dialogView);


        EditText productNameEditText = dialogView.findViewById(R.id.editTextProductName);
        Spinner productTypeSpinner = dialogView.findViewById(R.id.spinnerProductType);
        EditText priceEditText = dialogView.findViewById(R.id.editTextPrice);
        Button productImageView = dialogView.findViewById(R.id.imageViewProduct);
        Spinner sizeSpinner = dialogView.findViewById(R.id.spinnerSize);
        EditText mountEditText = dialogView.findViewById(R.id.editTextMount);


        ArrayAdapter<CharSequence> productTypeAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.product_types, android.R.layout.simple_spinner_item);
        productTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productTypeSpinner.setAdapter(productTypeAdapter);


        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.product_sizes, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUESTS_CODE_FOLDER);
            }
        });
        // Xử lý sự kiện khi nhấn nút "Thêm sản phẩm"
        builder.setPositiveButton("Thêm sản phẩm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = generateID();
                String productName = productNameEditText.getText().toString();
                String productType = productTypeSpinner.getSelectedItem().toString();
                double price = Double.parseDouble(priceEditText.getText().toString());
                String size = sizeSpinner.getSelectedItem().toString();
                int mount = Integer.parseInt(mountEditText.getText().toString());

                if(id.isEmpty()|| productName.isEmpty()|| productType.isEmpty()|| size.isEmpty()||mount <=0||price<=0){
                    Toast.makeText(getActivity(), "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }else{
                    if (productimage != null) {
                        StorageReference cvRef = storageRef.child("Products/" + id + "/product.jpg");
                        ByteArrayOutputStream cvStream = new ByteArrayOutputStream();
                        productimage.compress(Bitmap.CompressFormat.PNG, 100, cvStream);
                        byte[] cvBytes = cvStream.toByteArray();
                        UploadTask uploadTask = cvRef.putBytes(cvBytes);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                cvRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        productImage = uri.toString();
                                    }
                                });
                            }
                        });
                    }

                }
                addproducts(id,productName,productType,productImage,price,size,mount);


                dialog.dismiss();
            }
        });

        // Xử lý sự kiện khi nhấn nút "Hủy"
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng dialog
                dialog.dismiss();
            }
        });

        // Hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUESTS_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                productimage = BitmapFactory.decodeStream(inputStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            super.onActivityResult(requestCode, resultCode, data);
        }


    }
    private void addproducts(String id, String productName,String productType, String productImage,double price, String size, int mount){
        addproduct_api.api.add_products(id,productName,productType,productImage,price,size,mount).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                Toast.makeText(getActivity(), "oke ngay", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                String errorMessage = "mệt r " + t.getMessage();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}