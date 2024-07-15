package com.example.travelreminder.workers;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.travelreminder.model.Trip;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ScheduleWork {

   private static String REMIND_UNIQUE_TAG = "Remind tag ";

   public static void tripRemoteWork(Context context, String status, String tripID, Trip trip){
      Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
      Gson gson = new Gson();
      String tripString = gson.toJson(trip);
      Data inputData = new Data.Builder()
              .putString(TripRemoteWorker.WORK_STATUS_KEY, status)
              .putString(TripRemoteWorker.WORK_TRIP_KEY, tripString)
              .putString(TripRemoteWorker.WORK_TRIP_ID_KEY, tripID)
              .build();
      OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(TripRemoteWorker.class)
              .setConstraints(constraints)
              .setInputData(inputData)
              .build();
      WorkManager workManager = WorkManager.getInstance(context);
      workManager.enqueue(request);
   }
   public static void tripReminder(Context context, String tripID,Trip trip){
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
      String dateTimeString = trip.getDate() + " " + trip.getTime();
      long timestamp;
      try {
         Date date = dateFormat.parse(dateTimeString);
         timestamp = date.getTime();
      } catch (ParseException e) {
         e.printStackTrace();
         return;
      }
      String tripGson = new Gson().toJson(trip);
      Data data = new Data.Builder().putString(RemindTripWorker.TRIP_RECEIVER_KEY, tripGson).build();
      long duration = timestamp - System.currentTimeMillis();
      if(duration < 0)
         duration = 0;
      OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(RemindTripWorker.class)
              .setInputData(data)
              .setInitialDelay(duration, TimeUnit.MILLISECONDS)
              .build();
      WorkManager workManager = WorkManager.getInstance(context);
      workManager.enqueueUniqueWork(REMIND_UNIQUE_TAG + tripID, ExistingWorkPolicy.REPLACE, request);
   }
   public static void cancelReminder(Context context, String tripID){
      REMIND_UNIQUE_TAG = "remind ";
      WorkManager.getInstance(context).cancelUniqueWork(REMIND_UNIQUE_TAG + tripID);
   }
}
