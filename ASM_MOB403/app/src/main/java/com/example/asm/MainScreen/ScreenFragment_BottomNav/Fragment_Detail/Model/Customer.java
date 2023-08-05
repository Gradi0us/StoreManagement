package com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model;

import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    String id;
    String customername;
    int customerphone;
    private List<Products> productsList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public int getCustomerphone() {
        return customerphone;
    }

    public void setCustomerphone(int customerphone) {
        this.customerphone = customerphone;
    }


    public Customer(String id, String customername, int customerphone ) {
        this.id = id;
        this.customername = customername;
        this.customerphone = customerphone;

    }

    public Customer() {
    }
    public List<Products> getProductList() {
        return productsList;
    }
    public void setProductList(List<Products> productsList) {
        this.productsList = productsList;
    }
}
