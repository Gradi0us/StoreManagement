package com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.add_history;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.addnew_bill;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.API.addnew_customer;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.API.getitem_list_api;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.API.search_existed_customer;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Adapter.Detail_Adapter;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Bill;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model.Customer;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.History;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;
import com.example.asm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Adapter.Detail_Adapter.productmount;
public class ProductDetailFragment extends Fragment {

    private Products product;
    private ImageView imageViewProduct;
    private TextView textViewProductName, textViewProductType, textViewPrice, textViewSize, textViewMount, idproduct;
    private Button createbill, addonbill;
    //    MainScreen mainScreen;
    String uid, customername, customerid;
    int customerphone;
    SharedPreferences sharedPreferences;
    String phoneNumberInput;
    String id;
    String pid;
    String date;
    Calendar calendar;
    SimpleDateFormat sdf;
    int totalcost;
    String status = "pending";
    List<Products> productList = new ArrayList<>();
    Detail_Adapter adapter;
    RecyclerView billcreate;
    LinearLayoutManager manager1;
    int productmountt, cost;
    TextView tottalcost;

    public static ProductDetailFragment newInstance(Products product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("product", product);
        fragment.setArguments(args);
        // Set the MainScreen reference here
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (Products) getArguments().getSerializable("product");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        // Call Function
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("userId", "");
        IntentFilter intentFilter = new IntentFilter("PRODUCT_MOUNT_CHANGED");
        getActivity().registerReceiver(productMountBroadcastReceiver, intentFilter);

        // lấy dữ liệu
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = sdf.format(calendar.getTime());

        anhxa(view);
        getdata_toitem(product, view);
        totalcost = productmountt * cost;
        adapter = new Detail_Adapter(getActivity(), productList);

        createbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProductDialog();
            }
        });

        addonbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code here
            }
        });

        return view;
    }

    private void anhxa(View view) {
        createbill = view.findViewById(R.id.create_bill);
        addonbill = view.findViewById(R.id.addonbill);
        idproduct = view.findViewById(R.id.id_product);
        imageViewProduct = view.findViewById(R.id.imageViewProduct);
        textViewProductName = view.findViewById(R.id.textViewProductName);
        textViewProductType = view.findViewById(R.id.textViewProductType);
        textViewPrice = view.findViewById(R.id.textViewPrice);
        textViewSize = view.findViewById(R.id.textViewSize);
        textViewMount = view.findViewById(R.id.textViewMount);

    }

    private void getdata_toitem(Products product, View view) {
        if (product != null) {
            pid = product.getId();
            Number priceNum = product.getPrice();
            cost = priceNum.intValue();

            idproduct.setText(pid);
            Glide.with(view).load(product.getProductimage()).into(imageViewProduct);
            textViewProductName.setText(product.getProductname());
            textViewProductType.setText(product.getProducttype());
            textViewPrice.setText(String.valueOf(product.getPrice()));
            textViewSize.setText(product.getSize());
            textViewMount.setText(String.valueOf(product.getMount()));
        }
    }

    private void showAddProductDialog() {
        id = generateID();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.FullscreenAlertDialog);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_bill, null, false);
        builder.setView(dialogView);

        EditText phonecustoerEditText = dialogView.findViewById(R.id.edittextcustomerphonenumber);
        EditText namecustomerEditText = dialogView.findViewById(R.id.edittextnamecustomer);
        TextView namecustomer = dialogView.findViewById(R.id.txt_namecustomer);
        TextView idnhanvien = dialogView.findViewById(R.id.idnhanvien);
        Button thanhtoan = dialogView.findViewById(R.id.thanhtoan);
        Button muatiep = dialogView.findViewById(R.id.muatiep);
        tottalcost = dialogView.findViewById(R.id.totalcost);
        totalcost = productmountt * cost;
        tottalcost.setText(String.valueOf(totalcost));
        tottalcost.setText(String.valueOf(cost));
        billcreate = dialogView.findViewById(R.id.billcreate);

        if (pid != null) {
            getitem_id(pid);
        } else {
            Toast.makeText(getActivity(), "productid null", Toast.LENGTH_SHORT).show();
        }

        idnhanvien.setText(uid);
        namecustomer.setVisibility(View.GONE);
        namecustomerEditText.setVisibility(View.GONE);


        phonecustoerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                namecustomer.setVisibility(View.GONE);
                namecustomerEditText.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNumberInput = s.toString();
                if (!phoneNumberInput.isEmpty() && TextUtils.isDigitsOnly(phoneNumberInput)) {
                    customerphone = Integer.parseInt(phoneNumberInput);
                    customername = namecustomerEditText.getText().toString().trim();
                    search_existed_customer.api.getcustomerinfo(customerphone).enqueue(new Callback<List<Customer>>() {
                        @Override
                        public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                            if (response.isSuccessful()) {
                                List<Customer> customers = response.body();
                                if (customers != null && customers.size() > 0) {
                                    Customer customer = customers.get(0);
                                    String customername1 = customer.getCustomername();
                                    customerid = customer.getId();
                                    namecustomerEditText.setVisibility(View.GONE);
                                    namecustomer.setVisibility(View.VISIBLE);
                                    namecustomer.setText(customername1);
                                } else {
                                    namecustomer.setVisibility(View.GONE);
                                    namecustomerEditText.setVisibility(View.VISIBLE);

                                }
                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Customer>> call, Throwable t) {
                            Toast.makeText(getActivity(), "Lỗi:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                customername = namecustomerEditText.getText().toString().trim();

            }

        });

//        Map<String, Object> billdata = new HashMap<>();
//        Map<String, Object> customerdata = new HashMap<>();


        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customerid == null) {
                    customername = namecustomerEditText.getText().toString().trim();
                    addnew_customer.api.addnewcustomer(customerphone, customername, id).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            if (response.isSuccessful()) {
                                Customer customers = response.body();
                                if (customers != null) {
                                    Toast.makeText(getActivity(), "thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
                                    search_existed_customer.api.getcustomerinfowithphoneandusername(customername,customerphone).enqueue(new Callback<List<Customer>>() {
                                        @Override
                                        public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                                            if (response.isSuccessful()) {
                                                List<Customer> customers = response.body();
                                                if (customers != null && customers.size() > 0) {
                                                    Customer customer = customers.get(0);

                                                    String customerid = customer.getId();
                                                    if(customerid != null ){
                                                        add_history(id, uid, pid, date, customerid, productmountt, totalcost);
                                                    }else{
                                                        Toast.makeText(getActivity(), "customerid trống", Toast.LENGTH_SHORT).show();
                                                    }

                                                }else{
                                                    Toast.makeText(getActivity(), "không có customer ", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Customer>> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {
                            Toast.makeText(getActivity(), "Lỗi" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (pid != null && id != null && uid != null && date != null) {
                        add_history(id, uid, pid, date, customerid, productmountt, totalcost);
                    } else {
                        Toast.makeText(getActivity(), "Dữ liệu trống", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        muatiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customerid.equals("")) {
                    customername = namecustomerEditText.getText().toString().trim();
                    if (customerphone != 0 && customername != null) {
                        addnew_customer(id, customerphone, customername);
                    }

                } else {
                    id = generateID();
                    addnewbill(id, uid, pid, customerid, date, totalcost, status);
                }


            }
        });


        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                productList.clear();
            }
        });
        dialog.show();

    }

    public static String generateID() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomNumber = generateRandomNumber();
        return timestamp + randomNumber;
    }

    private static String generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(90) + 10;
        return String.valueOf(randomNumber);
    }

    private void addnew_customer(String id, int customerphone, String customername) {
        addnew_customer.api.addnewcustomer(customerphone, customername, id).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Customer customers = response.body();
                    if (customers != null) {
                        Toast.makeText(getActivity(), "thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
                        String customerid = customers.getId();
                        if (customerid != null) {
                            addnewbill(id, uid, pid, customerid, date, totalcost, status);
                        } else {
                            Toast.makeText(getActivity(), "customerid null r", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addnewbill(String id, String uid, String pid, String customerid, String date, int totalcost, String status) {
        addnew_bill.api.addnewbill(id, uid, pid, customerid, date, totalcost, status).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Thanhcong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getitem_id(String pid) {
        getitem_list_api.api.getitemproductid(pid).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {
                    Products product = response.body();
                    if (product != null) {
                        productList.add(product);
                        manager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        billcreate.setLayoutManager(manager1);
                        billcreate.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private BroadcastReceiver productMountBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("PRODUCT_MOUNT_CHANGED")) {
                int newProductMount = intent.getIntExtra("PRODUCT_MOUNT", 1);
                // Nhận dữ liệu số lượng sản phẩm mới từ BroadcastReceiver và xử lý
                // Ví dụ:
                productmountt = newProductMount; // Lưu giá trị vào biến của Fragment
                totalcost = productmountt * cost;
                tottalcost.setText(String.valueOf(totalcost));
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Hủy đăng ký BroadcastReceiver khi Fragment bị hủy
        getActivity().unregisterReceiver(productMountBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Đăng ký BroadcastReceiver khi Fragment được resume
        IntentFilter intentFilter = new IntentFilter("PRODUCT_MOUNT_CHANGED");
        getActivity().registerReceiver(productMountBroadcastReceiver, intentFilter);
    }

    private void add_history(String id, String uid, String pid, String date, String customerid, int mountbuy, int price) {
        add_history.api.addnewhistory(id, uid, pid, date, customerid, mountbuy, price).enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi Thêm lịch sử"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
