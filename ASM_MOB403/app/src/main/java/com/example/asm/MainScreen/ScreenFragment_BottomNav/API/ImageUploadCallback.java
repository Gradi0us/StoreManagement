package com.example.asm.MainScreen.ScreenFragment_BottomNav.API;

public interface ImageUploadCallback {
    void onUploadSuccess(String imageUrl);
    void onUploadFailure(String errorMessage);
}
