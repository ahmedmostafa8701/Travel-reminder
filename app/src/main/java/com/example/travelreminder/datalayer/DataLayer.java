package com.example.travelreminder.datalayer;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.work.ListenableWorker;

import com.example.travelreminder.datalayer.local.ILocalDataLayer;
import com.example.travelreminder.datalayer.local.LocalDataLayer;
import com.example.travelreminder.datalayer.remote.FirebaseDataLayer;
import com.example.travelreminder.datalayer.remote.IRemoteDataLayer;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;

public class DataLayer implements IDatalayer<LiveData<ListenableWorker.Result>>{
   ILocalDataLayer localDataLayer;
   IRemoteDataLayer remoteDataLayer;

   public DataLayer(Context context) {
      localDataLayer = new LocalDataLayer(context);
      remoteDataLayer = new FirebaseDataLayer();
   }

   @Override
   public LiveData<ListenableWorker.Result> loadUser() {
      localDataLayer.loadUser();
      return remoteDataLayer.loadUser();
   }

   @Override
   public LiveData<ListenableWorker.Result> loadTrips() {
      localDataLayer.loadTrips();
      return remoteDataLayer.loadTrips();
   }

   @Override
   public LiveData<ListenableWorker.Result> addUser(User user) {
      localDataLayer.addUser(user);
      return remoteDataLayer.addUser(user);
   }

   @Override
   public LiveData<ListenableWorker.Result> addTrip(Trip trip) {
      localDataLayer.addTrip(trip);
      return remoteDataLayer.addTrip(trip);
   }

   @Override
   public LiveData<ListenableWorker.Result> removeUser() {
      localDataLayer.removeUser();
      return remoteDataLayer.removeUser();
   }

   @Override
   public LiveData<ListenableWorker.Result> removeTrip(String tripID) {
      localDataLayer.removeTrip(tripID);
      return remoteDataLayer.removeTrip(tripID);
   }

   @Override
   public LiveData<ListenableWorker.Result> updateTrip(String tripID, Trip update) {
      localDataLayer.updateTrip(tripID, update);
      return remoteDataLayer.updateTrip(tripID, update);
   }

   @Override
   public LiveData<ListenableWorker.Result> addProfileImage(Bitmap bitmap) {
      localDataLayer.addProfileImage(bitmap);
      return remoteDataLayer.addProfileImage(bitmap);
   }

   @Override
   public LiveData<ListenableWorker.Result> loadProfileImage() {
      localDataLayer.loadProfileImage();
      return remoteDataLayer.loadProfileImage();
   }
}
