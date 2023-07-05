package com.example.travelreminder.workers;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.travelreminder.model.Trip;
import com.google.gson.Gson;

public class ScheduleWork {
   public static void tripRemoteWork(Context context, String status, Trip trip){
      Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
      Gson gson = new Gson();
      String tripString = gson.toJson(trip);
      Data inputData = new Data.Builder()
              .putString(TripWorker.WORK_STATUS_KEY, status)
              .putString(TripWorker.WORK_TRIP_KEY, tripString)
              .build();
      OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(TripWorker.class)
              .setConstraints(constraints)
              .setInputData(inputData)
              .build();
      WorkManager workManager = WorkManager.getInstance(context);
      workManager.enqueue(request);
   }
}
