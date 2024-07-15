package com.example.travelreminder.datalayer.local;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;

public class LocalDataLayer implements ILocalDataLayer{
   ILocalDataLayer dataLayer;
   Context context;
   public LocalDataLayer(ILocalDataLayer dataLayer) {
      this.dataLayer = dataLayer;
   }

   public LocalDataLayer(Context context) {
      dataLayer = new SQLiteDataLayer(context);
      this.context = context;
   }

   @Override
   public Void loadUser() {
      return dataLayer.loadUser();
   }

   @Override
   public Void loadTrips() {
      return dataLayer.loadTrips();
   }

   @Override
   public Void addUser(User user) {
      return dataLayer.addUser(user);
   }

   @Override
   public Void addTrip(Trip trip) {
      return dataLayer.addTrip(trip);
   }

   @Override
   public Void removeUser() {
      return dataLayer.removeUser();
   }

   @Override
   public Void removeTrip(String tripID) {
      return dataLayer.removeTrip(tripID);
   }

   @Override
   public Void updateTrip(String tripID, Trip update) {
      return dataLayer.updateTrip(tripID, update);
   }

   @Override
   public Void addProfileImage(Bitmap bitmap) {
      return dataLayer.addProfileImage(bitmap);
   }

   @Override
   public Void loadProfileImage() {
      return dataLayer.loadProfileImage();
   }
}
