package com.example.travelreminder.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.travelreminder.R;
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
        createCityList();
    }

    private void createNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel),
                    "trip",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This is trip channel");
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    private void createCityList() {

    }
}
