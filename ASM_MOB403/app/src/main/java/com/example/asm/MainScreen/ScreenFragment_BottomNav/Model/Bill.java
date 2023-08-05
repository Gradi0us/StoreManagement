package com.example.asm.MainScreen.ScreenFragment_BottomNav.Model;

import com.google.type.DateTime;

public class Bill {
    String id;
    String uid;
    String pid;
    String customerid;
    String date;
    int totalcost;

    public int getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(int totalcost) {
        this.totalcost = totalcost;
    }

    public Bill(int totalcost) {
        this.totalcost = totalcost;
    }

    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bill(String id, String uid, String pid, String customerid, String date, String status) {
        this.id = id;
        this.uid = uid;
        this.pid = pid;
        this.customerid = customerid;

        this.date = date;
        this.status = status;
    }

    public Bill() {
    }
}
