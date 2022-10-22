package com.example.travelreminder;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.travelreminder.pojo.Note;
import com.example.travelreminder.pojo.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class FirebaseManager {
    private static int size = 0;
    public static final String dataCollection = "users";
    public static final String tripCollection = "trips";
    public static final String imageCollection = "image";
    public static final String phoneCollection = "phone";
    public static final String userNameCollection = "user name";
    public static final String noteCollection = "notes";
    private DatabaseReference reference;
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
    public void addUser(FirebaseUser user, String userName, Bitmap image, String phone){
        reference.child(userNameCollection).setValue(userName);
        reference.child(imageCollection).setValue(image);
        reference.child(phoneCollection).setValue(phone);
    }
    public void addTrip(Trip trip){
        DatabaseReference ref = reference.child(tripCollection).push();
        trip.setKey(ref.getKey());
        ref.setValue(trip);
        size++;
    }
    public void editTrip(String key, Trip trip){
        reference.child(tripCollection).child(key).setValue(trip);
    }
    public void addNoteToTrip(String key, Note note){
        DatabaseReference ref = reference.child(tripCollection).child(key).push();
        note.setKey(ref.getKey());
        ref.setValue(note);
    }
    public void deleteTrip(String key){
        reference.child(tripCollection).child(key).removeValue();
        size--;
    }
    public int getSize(){
        return size;
    }
    public String getUserName(){
        return "user";
/*        (String) reference.child(userNameCollection).get().getResult().getValue()*/
    }
    public Bitmap getProfileImage(){
        return null;
    }
    public String getEmail(){
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
}
