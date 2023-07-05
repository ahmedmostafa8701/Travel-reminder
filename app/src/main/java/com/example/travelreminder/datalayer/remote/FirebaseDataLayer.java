package com.example.travelreminder.datalayer.remote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.ListenableWorker.Result;

import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDataLayer implements IRemoteDataLayer {

    private final RunTimeData runTimeData = RunTimeData.instance;
    private final FirebaseReferences ref = FirebaseReferences.getInstance();

    @Override
    public MutableLiveData<Result> loadUser() {
        MutableLiveData<Result> liveRes = new MutableLiveData<>();
        DatabaseReference dataRef = ref.getUser();
        dataRef.get().addOnCompleteListener((task -> {
            if(task.isSuccessful()){
                User user = new User();
                try {
                    DataSnapshot result = task.getResult();
                    user.setUserName(result.child("userName").getValue(String.class));
                    user.setEmail(result.child("email").getValue(String.class));
                    user.setPhone(result.child("phone").getValue(String.class));
                }catch (Exception e){
                    e.printStackTrace();
                }
                runTimeData.setUser(user);
                loadTrips();
                liveRes.postValue(Result.success());
            }
            else{
                liveRes.postValue(Result.failure());
            }
        }));
        loadProfileImage();
        return liveRes;
    }
    @Override
    public MutableLiveData<Result> loadTrips() {
        MutableLiveData<Result> liveRes = new MutableLiveData<>();
        DatabaseReference dataRef = ref.getTrips();
        dataRef.get().addOnCompleteListener((task -> {
            if(task.isSuccessful()){
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
                liveRes.postValue(Result.success());
            }
            else{
                liveRes.postValue(Result.failure());
            }
        }));
        return liveRes;
    }
    @Override
    public MutableLiveData<Result> addUser(User user) {
        MutableLiveData<Result> liveRes = new MutableLiveData<>();
        runTimeData.setUser(user);
        User fUser = new User(user.getEmail(), user.getUserName(), user.getPhone(), user.getTrips());

        DatabaseReference dataRef = ref.getUsers();
        dataRef.child(FirebaseAuth.getInstance().getUid()).setValue(fUser).addOnCompleteListener((task -> {
            if(task.isSuccessful()){
                addProfileImage(user.getImage());
                liveRes.postValue(Result.success());
            }
            else{
                liveRes.postValue(Result.failure());
            }
        }));
        return liveRes;
    }

    @Override
    public MutableLiveData<Result> addTrip(Trip trip) {
        MutableLiveData<Result> liveRes = new MutableLiveData<>();
        DatabaseReference dataRef = ref.getTrips();
        dataRef.child(trip.getTripID()).setValue(trip).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                liveRes.postValue(Result.success());
            }
            else{
                liveRes.postValue(Result.failure());
            }
        });
        return liveRes;
    }

    @Override
    public MutableLiveData<Result> removeUser() {
        MutableLiveData<Result> liveRes = new MutableLiveData<>();
        DatabaseReference reference = FirebaseReferences.getInstance().getUser();
        reference.removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                liveRes.postValue(Result.success());
            }
            else{
                liveRes.postValue(Result.failure());
            }
        });
        return liveRes;
    }
    @Override
    public MutableLiveData<Result> removeTrip(String tripID) {
        MutableLiveData<Result> liveRes = new MutableLiveData<>();
        DatabaseReference reference = FirebaseReferences.getInstance().getTrip(tripID);
        reference.removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                liveRes.postValue(Result.success());
            }
            else{
                liveRes.postValue(Result.failure());
            }
        });
        return liveRes;
    }


    @Override
    public MutableLiveData<Result> updateTrip(String tripID, Trip update) {
        MutableLiveData<Result> liveRes = new MutableLiveData<>();
        DatabaseReference reference = FirebaseReferences.getInstance().getTrip(tripID);
        reference.setValue(update).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                liveRes.postValue(Result.success());
            }
            else{
                liveRes.postValue(Result.failure());
            }
        });
        return liveRes;
    }

    @Override
    public MutableLiveData<Result> addProfileImage(Bitmap bitmap) {
        MutableLiveData<Result> liveRes = new MutableLiveData<>();
        User user = runTimeData.getUser().getValue();
        user.setImage(bitmap);
        runTimeData.setUser(user);
        StorageReference storeRef = ref.getStorageRef().child("users/" + FirebaseAuth.getInstance().getUid() + "/images/" + ref.profileImageName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        storeRef.putBytes(data).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                liveRes.postValue(Result.success());
            }
            else{
                liveRes.postValue(Result.failure());
            }
        });
        return liveRes;
    }
    @Override
    public LiveData<Result> loadProfileImage() {
        MutableLiveData<Result> liveRes = new MutableLiveData<>();
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
                liveRes.postValue(Result.success());
            }
            else{
                liveRes.postValue(Result.failure());
            }
        });
        return liveRes;
    }
}
