package com.example.travelreminder.datalayer.remote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.travelreminder.datalayer.IDatalayer;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;
import com.example.travelreminder.model.RunTimeData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDataLayer implements IDatalayer {

    private final RunTimeData runTimeData = RunTimeData.instance;
    private final FirebaseReferences ref = FirebaseReferences.getInstance();

    @Override
    public void getUser() {
        FirebaseReferences ref = this.ref;
        DatabaseReference dataRef = ref.getUser();
        dataRef.get().addOnCompleteListener((task -> {
            if(task.isSuccessful()){
                User user = new User();
                try {
                    DataSnapshot result = task.getResult();
                    user.setUserName(result.child("userName").getValue(String.class));
                    user.setEmail(result.child("email").getValue(String.class));
                    user.setPhone(result.child("phone").getValue(String.class));
                    for (DataSnapshot trip : result.child("trips").getChildren()) {
                        Trip trip1 = new Trip();
                        for (DataSnapshot child : trip.getChildren()) {
                            if(child.getKey().equals("tripID")){
                                trip1.setTripID(child.getValue(String.class));
                            }
                            else if(child.getKey().equals("status")){
                                trip1.setStatus(child.getValue(String.class));
                            }
                            else if(child.getKey().equals("name")){
                                trip1.setName(child.getValue(String.class));
                            }
                            else if(child.getKey().equals("date")){
                                trip1.setDate(child.getValue(String.class));
                            }
                            else if(child.getKey().equals("time")){
                                trip1.setTime(child.getValue(String.class));
                            }
                            else if(child.getKey().equals("cityFrom")){
                                trip1.setCityFrom(child.getValue(String.class));
                            }
                            else if(child.getKey().equals("cityTo")){
                                trip1.setCityTo(child.getValue(String.class));
                            }
                        }
                        user.addTrip(trip1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                runTimeData.setUser(user);
            }
        }));
        getProfileImage();
    }
    @Override
    public void getTrips() {
        // not used yet
        FirebaseReferences ref = this.ref;
        DatabaseReference dataRef = ref.getTrips();
        dataRef.get().addOnCompleteListener((task -> {
            List<Trip> trips = new ArrayList<>();
            for (DataSnapshot child : task.getResult().getChildren()) {
                try {
                    trips.add(child.getValue(Trip.class));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            User user = runTimeData.getUser().getValue();
            user.setTrips(trips);
            runTimeData.setUser(user);
        }));
    }
    @Override
    public void addUser(User user) {
        runTimeData.setUser(user);
        User fUser = new User(user.getEmail(), user.getUserName(), user.getPhone(), user.getTrips());
        FirebaseReferences ref = this.ref;
        DatabaseReference dataRef = ref.getUsers();
        dataRef.child(FirebaseAuth.getInstance().getUid()).setValue(fUser).addOnCompleteListener((task -> {
            if(task.isSuccessful()){
                addProfileImage(user.getImage());
            }
        }));
    }

    @Override
    public void addTrip(Trip trip) {
        User user = runTimeData.getUser().getValue();
        user.addTrip(trip);
        runTimeData.setUser(user);
        FirebaseReferences ref = this.ref;
        DatabaseReference dataRef = ref.getTrips();
        String tripID = dataRef.push().getKey();
        trip.setTripID(tripID);
        dataRef.child(tripID).setValue(trip);
    }
    @Override
    public void addTrips(List<Trip> trips) {
        if(trips != null){
            for (Trip trip : trips) {
                addTrip(trip);
            }
        }
    }

    @Override
    public void removeTrip(String tripID) {
        User user = runTimeData.getUser().getValue();
        user.removeTrip(tripID);
        runTimeData.setUser(user);
        DatabaseReference reference = FirebaseReferences.getInstance().getTrip(tripID);
        reference.removeValue();
    }

    @Override
    public void updateTrip(String tripID, Trip update) {
        User user = runTimeData.getUser().getValue();
        user.updateTrip(tripID, update);
        runTimeData.setUser(user);
        DatabaseReference reference = FirebaseReferences.getInstance().getTrip(tripID);
        reference.setValue(update);
    }

    @Override
    public void addProfileImage(Bitmap bitmap) {
        User user = runTimeData.getUser().getValue();
        user.setImage(bitmap);
        runTimeData.setUser(user);
        FirebaseReferences ref = this.ref;
        StorageReference storeRef = ref.getStorageRef().child("users/" + FirebaseAuth.getInstance().getUid() + "/images/" + ref.profileImageName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        storeRef.putBytes(data);
    }
    @Override
    public void getProfileImage() {
        FirebaseReferences ref = this.ref;
        StorageReference storeRef = ref.getProfileImage();
        storeRef.getBytes(20 * 1024 * 1024).addOnCompleteListener((task)->{
            if(task.isSuccessful()){
                byte[] result = task.getResult();
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                    User user = runTimeData.getUser().getValue();
                    user.setImage(bitmap);
                    runTimeData.setUser(user);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
