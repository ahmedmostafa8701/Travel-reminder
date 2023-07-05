package com.example.travelreminder.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.travelreminder.datalayer.remote.FirebaseDataLayer;
import com.example.travelreminder.datalayer.remote.IRemoteDataLayer;
import com.example.travelreminder.model.Trip;
import com.google.gson.Gson;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TripWorker extends Worker {
   public final static String WORK_STATUS_KEY = "status";
   public final static String WORK_STATUS_REMOVE = "Remove";
   public final static String WORK_STATUS_ADD = "Add";
   public final static String WORK_STATUS_UPDATE = "Update";
   public final static String WORK_TRIP_KEY = "trip";
   private final CountDownLatch latch = new CountDownLatch(1);
   Context context;
   IRemoteDataLayer dataLayer;
   public TripWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
      super(context, workerParams);
      this.context = context;
      dataLayer = new FirebaseDataLayer();
   }

   @NonNull
   @Override
   public Result doWork() {
      Gson gson = new Gson();
      Trip trip = gson.fromJson(getInputData().getString(WORK_TRIP_KEY), Trip.class);
      String status = getInputData().getString(WORK_STATUS_KEY);
      LiveData<Result> result = new MutableLiveData<>(Result.failure());
      switch (status){
          case WORK_STATUS_ADD:
              result = dataLayer.addTrip(trip);
              break;
          case WORK_STATUS_REMOVE:
              result = dataLayer.removeTrip(trip.getTripID());
              break;
          case WORK_STATUS_UPDATE:
              result = dataLayer.updateTrip(trip.getTripID(), trip);
              break;
      }
      try {
         latch.await(3, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
         e.printStackTrace();
         return Result.failure();
      }
      Result result1 = result.getValue();
      if(result1 == null){
         return Result.failure();
      }
      return result1;
   }
}
