package com.example.travelreminder.pojo.datalayer.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseReferences {
    private DatabaseReference reference;
    private StorageReference storageRef;
    public String profileImageName = "profileImage";
    private FirebaseReferences() {
        reference = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
    }
    public DatabaseReference getUsers() {
        return reference.child("users");
    }
    public DatabaseReference getUser(){
        return getUsers().child(FirebaseAuth.getInstance().getUid());
    }
    public DatabaseReference getTrips() {
        return getUser().child("trips");
    }
    public DatabaseReference getTrip(String tripID) {
        return getTrips().child(tripID);
    }
    public StorageReference getImage(String name) {
        return storageRef.child("users/" + FirebaseAuth.getInstance().getUid() + "/images/" + name);
    }
    public StorageReference getProfileImage() {
        return getImage(profileImageName);
    }
    public StorageReference getStorageRef(){
        return storageRef;
    }
    public DatabaseReference getPhone() {
        return getUser().child("phone");
    }
    public DatabaseReference getName() {
        return getUser().child("userName");
    }

    public static FirebaseReferences getInstance() {
        return new FirebaseReferences();
    }
}
