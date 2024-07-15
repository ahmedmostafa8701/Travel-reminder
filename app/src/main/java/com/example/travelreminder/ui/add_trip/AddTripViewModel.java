package com.example.travelreminder.ui.add_trip;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.travelreminder.model.Trip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateTimeString = dateFormat.format(calendar.getTime());
        Random random = new Random();
        String tripId = currentDateTimeString + " " + random.nextInt(1000);
        trip.setTripID(tripId);
        repo.addTrip(trip);
    }
}
