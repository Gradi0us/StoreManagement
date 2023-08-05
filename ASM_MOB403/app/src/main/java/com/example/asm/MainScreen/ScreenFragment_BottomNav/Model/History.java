package com.example.asm.MainScreen.ScreenFragment_BottomNav.Model;

public class History {
    String id;
    String pid;
    String uid;
    String date;
    String customerid;

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public History(String customerid) {
        this.customerid = customerid;
    }

    int mountbuy,price;

    public int getMountbuy() {
        return mountbuy;
    }

    public void setMountbuy(int mountbuy) {
        this.mountbuy = mountbuy;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public History(int mountbuy, int price) {
        this.mountbuy = mountbuy;
        this.price = price;
    }

    public History(String id, String pid, String uid, String date) {
        this.id = id;
        this.pid = pid;
        this.uid = uid;
        this.date = date;
    }

    public History() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String bid) {
        this.pid = bid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
