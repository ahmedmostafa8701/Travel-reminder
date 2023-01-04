package com.example.travelreminder.pojo.datalayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.example.travelreminder.pojo.Trip;
import com.example.travelreminder.pojo.User;

import java.util.List;

public interface IDatalayer {
 public void getUser();
 public void getTrips();
 public void addUser(User user);
 public void addTrip(Trip trip);
 public void addTrips(List<Trip> trips);
 public void addProfileImage(Bitmap bitmap);
 public void getProfileImage();
 public boolean isAuth();
 public void signOut();
}
