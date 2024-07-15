package com.example.travelreminder.datalayer.remote;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.work.ListenableWorker;

import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;

public class RemoteDataLayer implements IRemoteDataLayer{
   IRemoteDataLayer dataLayer;

   public RemoteDataLayer(IRemoteDataLayer dataLayer) {
      this.dataLayer = dataLayer;
   }

   public RemoteDataLayer() {
      dataLayer = new FirebaseDataLayer();
   }

   @Override
   public LiveData<ListenableWorker.Result> loadUser() {
      return dataLayer.loadUser();
   }

   @Override
   public LiveData<ListenableWorker.Result> loadTrips() {
      return dataLayer.loadTrips();
   }

   @Override
   public LiveData<ListenableWorker.Result> addUser(User user) {
      return dataLayer.addUser(user);
   }

   @Override
   public LiveData<ListenableWorker.Result> addTrip(Trip trip) {
      return dataLayer.addTrip(trip);
   }

   @Override
   public LiveData<ListenableWorker.Result> removeUser() {
      return dataLayer.removeUser();
   }

   @Override
   public LiveData<ListenableWorker.Result> removeTrip(String tripID) {
      return dataLayer.removeTrip(tripID);
   }

   @Override
   public LiveData<ListenableWorker.Result> updateTrip(String tripID, Trip update) {
      return dataLayer.updateTrip(tripID, update);
   }

   @Override
   public LiveData<ListenableWorker.Result> addProfileImage(Bitmap bitmap) {
      return dataLayer.addProfileImage(bitmap);
   }

   @Override
   public LiveData<ListenableWorker.Result> loadProfileImage() {
      return dataLayer.loadProfileImage();
   }
}
