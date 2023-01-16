package com.example.travelreminder.pojo.datalayer;

import android.graphics.Bitmap;

import com.example.travelreminder.pojo.entities.Trip;
import com.example.travelreminder.pojo.entities.User;
import com.example.travelreminder.pojo.datalayer.remote.FirebaseDataLayer;

import java.util.List;

public class Repo implements IDatalayer {
    IDatalayer datalayer;

    public Repo(IDatalayer datalayer) {
        this.datalayer = datalayer;
    }

    public Repo() {
        this.datalayer = new FirebaseDataLayer();
    }

    @Override
    public void getUser() {
        datalayer.getUser();
    }

    @Override
    public void getTrips() {
        datalayer.getTrips();
    }

    @Override
    public void addUser(User user) {
        datalayer.addUser(user);
    }

    @Override
    public void addTrip(Trip trip) {
        datalayer.addTrip(trip);
    }

    @Override
    public void addTrips(List<Trip> trips) {
        datalayer.addTrips(trips);
    }

    @Override
    public void removeTrip(String tripID) {
        datalayer.removeTrip(tripID);
    }

    @Override
    public void updateTrip(String tripID, Trip update) {
        datalayer.updateTrip(tripID, update);
    }

    @Override
    public void addProfileImage(Bitmap bitmap) {
        datalayer.addProfileImage(bitmap);
    }

    @Override
    public void getProfileImage() {
        datalayer.getProfileImage();
    }

    @Override
    public boolean isAuth() {
        return datalayer.isAuth();
    }

    @Override
    public void signOut() {
        datalayer.signOut();
    }
}
