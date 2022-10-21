package com.example.travelreminder;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.travelreminder.pojo.Note;
import com.example.travelreminder.pojo.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FirebaseManager {
    private DatabaseReference reference;
    public static final String dataCollection = "users";
    public static final String tripCollection = "trips";
    public static final String imageCollection = "image";
    public static final String phoneCollection = "phone";
    public static final String userNameCollection = "user name";
    private static FirebaseManager instance;
    public static FirebaseManager getInstance(){
        if(instance == null){
            instance = new FirebaseManager();
        }
        return instance;
    }
    private FirebaseManager() {}
    public void setReference(DatabaseReference reference) {
        this.reference = reference;
    }

    public DatabaseReference getReference() {
        return reference;
    }
    public void addUser(String userName, Bitmap image, String phone){

    }
    public void addTrip(Trip trip){

    }
    public void editTrip(String key, Trip trip){

    }
    public void addNoteToTrip(String key, Note note){

    }
    public String getUserName(){
        return "user";
    }
    public Drawable getProfileImage(){
        return null;
    }
    public String getEmail(){
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
}
