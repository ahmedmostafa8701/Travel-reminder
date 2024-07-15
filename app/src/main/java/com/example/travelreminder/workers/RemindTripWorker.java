package com.example.travelreminder.workers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.travelreminder.R;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.services.CancelTripService;
import com.example.travelreminder.services.StartTripService;
import com.google.gson.Gson;

public class RemindTripWorker extends Worker {

   public static String TRIP_RECEIVER_KEY = "Trip receive key";
   Context context;
   public RemindTripWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
      super(context, workerParams);
      this.context = context;
   }

   @NonNull
   @Override
   public Result doWork() {
      String tripString = getInputData().getString(TRIP_RECEIVER_KEY);
      Gson gson = new Gson();
      Trip trip = gson.fromJson(tripString, Trip.class);
      Intent intent1 = new Intent(context, StartTripService.class);
      Intent intent2 = new Intent(context, CancelTripService.class);
      intent1.putExtra(StartTripService.TRIP_START_KEY, tripString);
      intent2.putExtra(CancelTripService.TRIP_CANCELED_KEY, tripString);
      PendingIntent pendingIntent1 = PendingIntent.getService(context, trip.getTripID().hashCode(), intent1, 0);
      PendingIntent pendingIntent2 = PendingIntent.getService(context, trip.getTripID().hashCode(), intent2, 0);
      Notification notification = new NotificationCompat.Builder(context, context.getString(R.string.notification_channel)).
              setContentTitle("Trip time on")
              .setContentText(trip.getName() + ": " + trip.getDate() + " " + trip.getTime())
              .setSmallIcon(R.drawable.travel_bag)
              .addAction(R.drawable.car, context.getString(R.string.start_trip_text), pendingIntent1)
              .addAction(android.R.drawable.ic_menu_close_clear_cancel, context.getString(R.string.cancel), pendingIntent2)
              .setPriority(Notification.PRIORITY_HIGH)
              .setAutoCancel(true)
              .build();
      NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
      notificationManager.notify(trip.getTripID().hashCode(), notification);
      return Result.success();
   }
}
