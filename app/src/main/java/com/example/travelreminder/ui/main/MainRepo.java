package com.example.travelreminder.ui.main;

import android.content.Context;

import com.example.travelreminder.Auth.Auth;
import com.example.travelreminder.datalayer.local.ILocalDataLayer;
import com.example.travelreminder.datalayer.local.SQLiteDataLayer;
import com.example.travelreminder.datalayer.remote.FirebaseDataLayer;
import com.example.travelreminder.datalayer.remote.IRemoteDataLayer;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.workers.ScheduleWork;
import com.example.travelreminder.workers.TripWorker;

public class MainRepo {
    Auth auth;
    IRemoteDataLayer remoteDataLayer;
    ILocalDataLayer localDataLayer;
    Context context;
    public MainRepo(Context context) {
        this.remoteDataLayer = new FirebaseDataLayer();
        this.localDataLayer = new SQLiteDataLayer(context);
        auth = new Auth();
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
        localDataLayer.removeTrip(tripID);
        RunTimeData.instance.removeTrip(tripID);
        Trip trip = new Trip();
        trip.setTripID(tripID);
        ScheduleWork.tripRemoteWork(context, TripWorker.WORK_STATUS_REMOVE, trip);
    }
}
