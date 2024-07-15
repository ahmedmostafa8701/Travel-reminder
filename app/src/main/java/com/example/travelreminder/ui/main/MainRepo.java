package com.example.travelreminder.ui.main;

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

public class MainRepo {
    IRemoteDataLayer remoteDataLayer;
    ILocalDataLayer localDataLayer;
    Context context;
    public MainRepo(Context context) {
        this.remoteDataLayer = new RemoteDataLayer();
        this.localDataLayer = new LocalDataLayer(context);
        this.context = context;
    }

    public void loadUser() {
        localDataLayer.loadUser();
        remoteDataLayer.loadUser();
    }
    public void removeUserLocal(){
        localDataLayer.removeUser();
    }

    public void removeTrip(String tripID) {
        RunTimeData.instance.removeTrip(tripID);
        localDataLayer.removeTrip(tripID);
        Trip trip = new Trip();
        trip.setTripID(tripID);
        ScheduleWork.tripRemoteWork(context, TripRemoteWorker.WORK_STATUS_REMOVE, trip.getTripID(), trip);
        ScheduleWork.cancelReminder(context, tripID);
        NotificationManagerCompat.from(context).cancel(trip.getTripID().hashCode());
    }

}
