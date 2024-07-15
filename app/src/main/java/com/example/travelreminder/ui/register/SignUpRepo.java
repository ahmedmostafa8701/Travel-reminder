package com.example.travelreminder.ui.register;

import android.content.Context;

import com.example.travelreminder.datalayer.local.ILocalDataLayer;
import com.example.travelreminder.datalayer.local.LocalDataLayer;
import com.example.travelreminder.datalayer.remote.IRemoteDataLayer;
import com.example.travelreminder.datalayer.remote.RemoteDataLayer;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.User;

public class SignUpRepo {

   IRemoteDataLayer remoteDataLayer;
   ILocalDataLayer localDataLayer;

   public SignUpRepo(Context context) {
      remoteDataLayer = new RemoteDataLayer();
      localDataLayer = new LocalDataLayer(context);
   }

   public void addUser(User user) {
      RunTimeData.instance.setUser(user);
      localDataLayer.addUser(user);
      remoteDataLayer.addUser(user);
   }
}
