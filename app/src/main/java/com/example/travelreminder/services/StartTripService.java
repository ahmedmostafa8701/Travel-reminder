package com.example.travelreminder.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
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

public class StartTripService extends Service {

    public static final String TRIP_START_KEY = "Trip start key";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String tripString = intent.getStringExtra(TRIP_START_KEY);
        Gson gson = new Gson();
        Trip trip = gson.fromJson(tripString, Trip.class);
        startTrip(trip);
        updateTrip(trip);
        NotificationManagerCompat.from(this).cancel(trip.getTripID().hashCode());
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startTrip(Trip trip) {
        String origin = trip.getCityFrom();
        String destination = trip.getCityTo();
        Uri directionsUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + origin + "&destination=" + destination);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, directionsUri);
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void updateTrip(Trip trip) {
        trip.setStatus(Status.Done.toString());
        ILocalDataLayer localDataLayer = new SQLiteDataLayer(this);
        localDataLayer.updateTrip(trip.getTripID(), trip);
        ScheduleWork.tripRemoteWork(this, TripRemoteWorker.WORK_STATUS_UPDATE, trip.getTripID(), trip);
        ScheduleWork.cancelReminder(this, trip.getTripID());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
