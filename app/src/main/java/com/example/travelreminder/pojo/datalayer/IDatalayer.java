package com.example.travelreminder.pojo.datalayer;

import android.graphics.Bitmap;

import com.example.travelreminder.pojo.entities.Trip;
import com.example.travelreminder.pojo.entities.User;

import java.util.List;

public interface IDatalayer {
 public void getUser();
 public void getTrips();
 public void addUser(User user);
 public void addTrip(Trip trip);
 public void addTrips(List<Trip> trips);
 public void removeTrip(String tripID);
 public void updateTrip(String tripID, Trip update);
 public void addProfileImage(Bitmap bitmap);
 public void getProfileImage();
 public boolean isAuth();
 public void signOut();
}
