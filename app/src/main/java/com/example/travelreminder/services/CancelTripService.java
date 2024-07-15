package com.example.travelreminder.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.example.travelreminder.datalayer.local.ILocalDataLayer;
import com.example.travelreminder.datalayer.local.SQLiteDataLayer;
import com.example.travelreminder.model.Status;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.workers.ScheduleWork;
import com.example.travelreminder.workers.TripRemoteWorker;
import com.google.gson.Gson;

public class CancelTripService extends Service {
   public static String TRIP_CANCELED_KEY = "trip cancel key";
   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      String tripString = intent.getStringExtra(TRIP_CANCELED_KEY);
      Gson gson = new Gson();
      Trip trip = gson.fromJson(tripString, Trip.class);
      cancelTrip(trip);
      NotificationManagerCompat.from(this).cancel(trip.getTripID().hashCode());
      stopSelf();
      return super.onStartCommand(intent, flags, startId);
   }

   public void cancelTrip(Trip trip) {
      trip.setStatus(Status.Cancel.toString());
      ILocalDataLayer localDataLayer = new SQLiteDataLayer(this);
      localDataLayer.updateTrip(trip.getTripID(), trip);
      ScheduleWork.tripRemoteWork(this, TripRemoteWorker.WORK_STATUS_UPDATE, trip.getTripID(), trip);
   }

   @Nullable
   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }
}
