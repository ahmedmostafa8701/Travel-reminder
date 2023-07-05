package com.example.travelreminder.ui.add_trip;

import android.content.Context;

import com.example.travelreminder.datalayer.local.ILocalDataLayer;
import com.example.travelreminder.datalayer.local.SQLiteDataLayer;
import com.example.travelreminder.datalayer.remote.FirebaseDataLayer;
import com.example.travelreminder.datalayer.remote.IRemoteDataLayer;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.workers.ScheduleWork;
import com.example.travelreminder.workers.TripWorker;

public class AddTripRepo {
    ILocalDataLayer localDataLayer;
    IRemoteDataLayer remoteDataLayer;
    Context context;

    public AddTripRepo(Context context) {
        localDataLayer = new SQLiteDataLayer(context);
        remoteDataLayer = new FirebaseDataLayer();
        this.context = context;
    }

    public void updateTrip(String tripID, Trip trip) {
        RunTimeData.instance.updateTrip(tripID, trip);
        localDataLayer.updateTrip(tripID, trip);
        ScheduleWork.tripRemoteWork(context, TripWorker.WORK_STATUS_UPDATE, trip);
    }

    public void addTrip(Trip trip) {
        RunTimeData.instance.addTrip(trip);
        localDataLayer.addTrip(trip);
        ScheduleWork.tripRemoteWork(context, TripWorker.WORK_STATUS_ADD, trip);
    }
}
