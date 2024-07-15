package com.example.travelreminder.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.travelreminder.datalayer.remote.IRemoteDataLayer;
import com.example.travelreminder.datalayer.remote.RemoteDataLayer;
import com.example.travelreminder.model.Trip;
import com.google.gson.Gson;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TripRemoteWorker extends Worker {
   public final static String WORK_STATUS_KEY = "Work status key";
   public final static String WORK_STATUS_REMOVE = "Work status remove";
   public final static String WORK_STATUS_ADD = "Work status add";
   public final static String WORK_STATUS_UPDATE = "Work status update";
   public final static String WORK_TRIP_KEY = "Work trip key";
    public static final String WORK_TRIP_ID_KEY = "Trip id key";
    private final CountDownLatch latch = new CountDownLatch(1);
   Context context;
   IRemoteDataLayer remoteDataLayer;
   public TripRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
      super(context, workerParams);
      this.context = context;
      remoteDataLayer = new RemoteDataLayer();
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
              result = remoteDataLayer.addTrip(trip);
              break;
          case WORK_STATUS_REMOVE:
              result = remoteDataLayer.removeTrip(trip.getTripID());
              break;
          case WORK_STATUS_UPDATE:
              String tripID = getInputData().getString(WORK_TRIP_ID_KEY);
              result = remoteDataLayer.updateTrip(tripID, trip);
              break;
      }
      try {
         latch.await(3, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
         e.printStackTrace();
         return Result.retry();
      }
      Result result1 = result.getValue();
      if(result1 == null){
         return Result.retry();
      }
      return result1;
   }
}
