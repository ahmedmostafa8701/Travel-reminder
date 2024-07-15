package com.example.travelreminder.datalayer.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.travelreminder.database.DatabaseHelper;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDataLayer implements ILocalDataLayer {
   Context context;
   private SQLiteDatabase database;
   public SQLiteDataLayer(Context context){
      this.context = context;
      DatabaseHelper helper = new DatabaseHelper(context);
      database = helper.getWritableDatabase();
   }
   @Override
   public Void loadUser() {
      User user = RunTimeData.instance.getUser().getValue();
      Cursor cursor = database.query(DatabaseHelper.USER,
              new String []{DatabaseHelper.USERNAME, DatabaseHelper.EMAIL, DatabaseHelper.PHONE},
              null, null, null, null, null);
      if(cursor != null && cursor.moveToFirst()){
         user.setUserName(cursor.getString(0));
         user.setEmail(cursor.getString(1));
         user.setPhone((cursor.getString(2)));
      }
      RunTimeData.instance.setUser(user);
      loadTrips();
      loadProfileImage();
      return null;
   }

   @Override
   public Void loadTrips() {
      User user = RunTimeData.instance.getUser().getValue();
      Cursor cursor = database.query(DatabaseHelper.TRIP,
              new String []{DatabaseHelper.ID, DatabaseHelper.NAME, DatabaseHelper.CITY_TO, DatabaseHelper.CITY_FROM,
                      DatabaseHelper.DATE, DatabaseHelper.TIME, DatabaseHelper.STATUS},
              null, null, null, null, null);
      List<Trip> trips = new ArrayList<>();
      if(cursor != null && cursor.moveToFirst()){
         do{
            Trip trip = new Trip();
            trip.setTripID(cursor.getString(0));
            trip.setName(cursor.getString(1));
            trip.setCityTo(cursor.getString(2));
            trip.setCityFrom(cursor.getString(3));
            trip.setDate(cursor.getString(4));
            trip.setTime(cursor.getString(5));
            trip.setStatus(cursor.getString(6));
            trips.add(trip);
         }while (cursor.moveToNext());
      }
      user.setTrips(trips);
      RunTimeData.instance.setUser(user);
      return null;
   }

   @Override
   public Void addUser(User user) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(DatabaseHelper.USERNAME, user.getUserName());
      contentValues.put(DatabaseHelper.EMAIL, user.getEmail());
      contentValues.put(DatabaseHelper.PHONE, user.getPhone());
      database.insert(DatabaseHelper.USER, null, contentValues);
      addProfileImage(user.getImage());
      return null;
   }

   @Override
   public Void addTrip(Trip trip) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(DatabaseHelper.ID, trip.getTripID());
      contentValues.put(DatabaseHelper.NAME, trip.getName());
      contentValues.put(DatabaseHelper.DATE, trip.getDate());
      contentValues.put(DatabaseHelper.TIME, trip.getTime());
      contentValues.put(DatabaseHelper.CITY_TO, trip.getCityTo());
      contentValues.put(DatabaseHelper.CITY_FROM, trip.getCityFrom());
      contentValues.put(DatabaseHelper.STATUS, trip.getStatus());
      database.insert(DatabaseHelper.TRIP, null, contentValues);
      return null;
   }
   @Override
   public Void removeUser(){
      database.delete(DatabaseHelper.USER, null, null);
      return null;
   }
   @Override
   public Void removeTrip(String tripID) {
      database.delete(DatabaseHelper.TRIP, DatabaseHelper.ID + " = ? ", new String[]{tripID});
      return null;
   }

   @Override
   public Void updateTrip(String tripID, Trip update) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(DatabaseHelper.ID, update.getTripID());
      contentValues.put(DatabaseHelper.NAME, update.getName());
      contentValues.put(DatabaseHelper.DATE, update.getDate());
      contentValues.put(DatabaseHelper.TIME, update.getTime());
      contentValues.put(DatabaseHelper.CITY_TO, update.getCityTo());
      contentValues.put(DatabaseHelper.CITY_FROM, update.getCityFrom());
      contentValues.put(DatabaseHelper.STATUS, update.getStatus());
      database.update(DatabaseHelper.TRIP, contentValues,DatabaseHelper.ID + " = ? ", new String[]{tripID});
      return null;
   }

   @Override
   public Void addProfileImage(Bitmap bitmap) {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
      byte[] imageBytes = stream.toByteArray();
      ContentValues contentValues = new ContentValues();
      contentValues.put(DatabaseHelper.IMAGE, imageBytes);
      database.update(DatabaseHelper.USER, contentValues,null, null);
      return null;
   }

   @Override
   public Void loadProfileImage() {
      User user = RunTimeData.instance.getUser().getValue();
      Cursor cursor = database.query(DatabaseHelper.USER, new String []{DatabaseHelper.IMAGE}, null, null, null, null, null);
      if(cursor != null && cursor.moveToFirst()){
         byte[] imageBytes = cursor.getBlob(0);
         Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
         user.setImage(bitmap);
         RunTimeData.instance.setUser(user);
      }
      return null;
   }
}
