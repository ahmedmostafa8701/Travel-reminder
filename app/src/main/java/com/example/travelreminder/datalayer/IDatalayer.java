package com.example.travelreminder.datalayer;

import android.graphics.Bitmap;

import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;

public interface IDatalayer<T> {
 public T loadUser();
 public T loadTrips();
 public T addUser(User user);
 public T addTrip(Trip trip);
 public T removeUser();
 public T removeTrip(String tripID);
 public T updateTrip(String tripID, Trip update);
 public T addProfileImage(Bitmap bitmap);
 public T loadProfileImage();
}
