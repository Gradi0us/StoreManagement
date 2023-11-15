package com.example.asm.MainScreen.ScreenFragment_BottomNav;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asm.MainScreen.MainScreen;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.findcustomerbyid_api;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.findcustomerinpinpending_api;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.findproductbyid_api;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Adapter.Bill_Adapter;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Adapter.ProductAdapter;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Bill;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model.Customer;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;
import com.example.asm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillFragment extends Fragment {
    RecyclerView recyclerviewuser;
    private List<Customer> customerList = new ArrayList<>();
    private List<Products> productsList = new ArrayList<>();
    private Set<String> processedCustomerIds = new HashSet<>();
    private Map<String, List<String>> customerProductMap = new HashMap<>();
    private Handler handler = new Handler();
    MainScreen mainScreen;
    private ProgressDialog progressDialog;
    String uid, pid, customerid;
    Bill_Adapter bill_adapter;
    private boolean isLoading = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bill, container, false);
        mainScreen = (MainScreen) getActivity();
        uid = mainScreen.userId;
        anhxa(v);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getbillfromiduser(uid);


        return v;
    }

    private void anhxa(View v) {
        recyclerviewuser = v.findViewById(R.id.user);

    }

    private void getbillfromiduser(String uid) {
        findcustomerinpinpending_api.api.getuserhaspendingbill(uid).enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()) {
                    List<Bill> bills = response.body();
                    if (!bills.isEmpty()) {
                        for (Bill bill : bills) {
                            customerid = bill.getCustomerid();
                            pid = bill.getPid();
                            if (pid != null) {
                                if (!customerProductMap.containsKey(customerid)) {
                                    customerProductMap.put(customerid, new ArrayList<>());
                                }
                                customerProductMap.get(customerid).add(pid);
                                getproductbyid(pid);
                            } else {
                                Toast.makeText(mainScreen, "pidnullnull", Toast.LENGTH_SHORT).show();
                            }
                            if (!processedCustomerIds.contains(customerid)) {
                                processedCustomerIds.add(customerid);
                            }
                        }
                    }
                    // Move the call to queryAndLoadCustomerData here
                    queryAndLoadCustomerData();
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Toast.makeText(mainScreen, "Lỗi getbillfromuid" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    private void queryAndLoadCustomerData() {
        for (String id : processedCustomerIds) {
            findcustomerbyid_api.api.getcustomerbyid(id).enqueue(new Callback<List<Customer>>() {
                @Override
                public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                    if (response.isSuccessful()) {
                        List<Customer> fetchedCustomerList = response.body();
                        if (fetchedCustomerList != null && !fetchedCustomerList.isEmpty()) {
                            Customer customer = fetchedCustomerList.get(0);
                            customer.getProductList().clear();
                            List<String> associatedPids = customerProductMap.get(customer.getId());
                            if (associatedPids != null && !associatedPids.isEmpty()) {
                                for (String pid : associatedPids) {
                                    for (Products product : productsList) {
                                        if (product.getId().equals(pid)) {
                                            customer.getProductList().add(product);
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!customerList.contains(customer)) {
                                customerList.add(customer);
                            }
                            if (customerList.size() == processedCustomerIds.size()) {
                                // Move the call to checkLoadingState here
                                checkLoadingState();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Customer>> call, Throwable t) {
                    Toast.makeText(mainScreen, "Lỗi getcustomerbyid" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void getproductbyid(String id) {
        findproductbyid_api.api.getproductbyid(id).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                isLoading = false;
                if (response.isSuccessful()) {
                    List<Products> productList = response.body();
                    if (productList != null && !productList.isEmpty()) {
                        // Cập nhật dữ liệu vào productsList
                        productsList.addAll(productList);

                        if (customerList.size() == processedCustomerIds.size()) {
                            checkLoadingState();
                        } else {
                            Toast.makeText(mainScreen, "productidnull", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                isLoading = false;
                Toast.makeText(mainScreen, "Lỗi getproductbyid" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkLoadingState() {
        if (!isLoading) {
            // Đã tải xong dữ liệu, hiển thị danh sách
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            bill_adapter = new Bill_Adapter(getActivity(), customerList);
            LinearLayoutManager manager1 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            recyclerviewuser.setLayoutManager(manager1);
            recyclerviewuser.setAdapter(bill_adapter);
        }
    }


}