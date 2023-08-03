package com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model;

public class Customer {
    String id;
    String customername;
    int customerphone;


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
}
