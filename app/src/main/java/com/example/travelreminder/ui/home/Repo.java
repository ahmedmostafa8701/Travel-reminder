package com.example.travelreminder.ui.home;

import android.graphics.Bitmap;

import com.example.travelreminder.Auth.Auth;
import com.example.travelreminder.datalayer.IDatalayer;
import com.example.travelreminder.datalayer.remote.FirebaseDataLayer;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;

import java.util.List;

public class Repo {
    Auth auth;
    IDatalayer datalayer;

    public Repo(IDatalayer datalayer) {
        this.datalayer = datalayer;
        auth = new Auth();
    }

    public Repo() {
        this.datalayer = new FirebaseDataLayer();
        auth = new Auth();
    }

    public void getUser() {
        datalayer.getUser();
    }

    public void getTrips() {
        datalayer.getTrips();
    }

    public void addUser(User user) {
        datalayer.addUser(user);
    }

    public void addTrip(Trip trip) {
        datalayer.addTrip(trip);
    }

    public void addTrips(List<Trip> trips) {
        datalayer.addTrips(trips);
    }

    public void removeTrip(String tripID) {
        datalayer.removeTrip(tripID);
    }

    public void updateTrip(String tripID, Trip update) {
        datalayer.updateTrip(tripID, update);
    }

    public void addProfileImage(Bitmap bitmap) {
        datalayer.addProfileImage(bitmap);
    }

    public void getProfileImage() {
        datalayer.getProfileImage();
    }
}
