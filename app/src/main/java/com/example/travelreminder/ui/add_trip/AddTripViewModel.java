package com.example.travelreminder.ui.add_trip;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.travelreminder.model.Trip;

public class AddTripViewModel extends ViewModel {
    AddTripRepo repo;
    Context context;

    public void setContext(Context context){
        this.context = context;
        repo = new AddTripRepo(context);
    }
    public void updateTrip(String tripID, Trip trip) {
        repo.updateTrip(tripID, trip);
    }

    public void addTrip(Trip trip) {
        repo.addTrip(trip);
    }
}
