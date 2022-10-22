package com.example.travelreminder;

import android.graphics.Bitmap;

import com.example.travelreminder.pojo.Trip;

import java.util.List;

public class User {
    public String email;
    public String name;
    public String phone;

    public User(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
