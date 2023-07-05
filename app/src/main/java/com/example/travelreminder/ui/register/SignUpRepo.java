package com.example.travelreminder.ui.register;

import android.content.Context;

import com.example.travelreminder.datalayer.local.ILocalDataLayer;
import com.example.travelreminder.datalayer.local.SQLiteDataLayer;
import com.example.travelreminder.datalayer.remote.FirebaseDataLayer;
import com.example.travelreminder.datalayer.remote.IRemoteDataLayer;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.User;

public class SignUpRepo {

   IRemoteDataLayer remoteDataLayer;
   ILocalDataLayer localDataLayer;

   public SignUpRepo(Context context) {
      remoteDataLayer = new FirebaseDataLayer();
      localDataLayer = new SQLiteDataLayer(context);
   }

   public void addUser(User user) {
      RunTimeData.instance.setUser(user);
      localDataLayer.addUser(user);
      remoteDataLayer.addUser(user);
   }
}
