package com.example.travelreminder.ui.login;

import android.content.Context;

import com.example.travelreminder.datalayer.local.ILocalDataLayer;
import com.example.travelreminder.datalayer.local.SQLiteDataLayer;
import com.example.travelreminder.datalayer.remote.FirebaseDataLayer;
import com.example.travelreminder.datalayer.remote.IRemoteDataLayer;

public class LoginRepo {
   IRemoteDataLayer remoteDataLayer;
   ILocalDataLayer localDataLayer;

   public LoginRepo(Context context) {
      remoteDataLayer = new FirebaseDataLayer();
      localDataLayer = new SQLiteDataLayer(context);
   }

}
