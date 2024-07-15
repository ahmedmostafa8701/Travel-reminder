package com.example.travelreminder.ui.add_trip;

import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

import com.example.travelreminder.datalayer.local.ILocalDataLayer;
import com.example.travelreminder.datalayer.local.LocalDataLayer;
import com.example.travelreminder.datalayer.remote.IRemoteDataLayer;
import com.example.travelreminder.datalayer.remote.RemoteDataLayer;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.workers.ScheduleWork;
import com.example.travelreminder.workers.TripRemoteWorker;

public class AddTripRepo {
    ILocalDataLayer localDataLayer;
    IRemoteDataLayer remoteDataLayer;
    Context context;

    public AddTripRepo(Context context) {
        localDataLayer = new LocalDataLayer(context);
        remoteDataLayer = new RemoteDataLayer();
        this.context = context;
    }

    public void updateTrip(String tripID, Trip trip) {
        RunTimeData.instance.updateTrip(tripID, trip);
        localDataLayer.updateTrip(tripID, trip);
        ScheduleWork.tripRemoteWork(context, TripRemoteWorker.WORK_STATUS_UPDATE, trip.getTripID(), trip);
        ScheduleWork.tripReminder(context, tripID, trip);
        NotificationManagerCompat.from(context).cancel(trip.getTripID().hashCode());
    }

    public void addTrip(Trip trip) {
        RunTimeData.instance.addTrip(trip);
        localDataLayer.addTrip(trip);
        ScheduleWork.tripRemoteWork(context, TripRemoteWorker.WORK_STATUS_ADD, trip.getTripID(), trip);
        ScheduleWork.tripReminder(context, trip.getTripID(), trip);
    }
}
