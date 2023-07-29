package com.example.asm.MainScreen.ScreenFragment_DrawerNav.Model;

import java.sql.Timestamp;

public class CodeAuthenticate {
    String id;
    String code;
    Timestamp expiry_time;

    public CodeAuthenticate() {
    }

    public CodeAuthenticate(String id, String code, Timestamp expiry_time) {
        this.id = id;
        this.code = code;
        this.expiry_time = expiry_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getExpiry_time() {
        return expiry_time;
    }

    public void setExpiry_time(Timestamp expiry_time) {
        this.expiry_time = expiry_time;
    }
}
