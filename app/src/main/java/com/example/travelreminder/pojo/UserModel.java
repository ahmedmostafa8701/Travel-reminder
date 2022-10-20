package com.example.travelreminder.pojo;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel {
    private static UserModel instance;
    private static FirebaseAuth auth;
    private static FirebaseUser user;
    private static FirebaseDatabase db;
    private static DatabaseReference dbref;
    private UserModel(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
    }
    public static UserModel getInstance(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null){
            return null;
        }
        if(instance == null){
            instance = new UserModel();
        }
        return instance;
    }
    public String getEmail(){
        return user.getEmail();
    }
    public String getUserName(){
        return user.getDisplayName();
    }
    public String PhoneNumber(){
        return user.getPhoneNumber();
    }
    public Uri getImage(){
        return user.getPhotoUrl();
    }
}
