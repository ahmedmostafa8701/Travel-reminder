package com.example.travelreminder.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Trip implements Parcelable {
   private String tripID;
   private String name;
   private String date;
   private String time;
   private String cityFrom;
   private String cityTo;
   private String status;

   public Trip(String name, String date, String time, String cityFrom, String cityTo, String status) {
      this.name = name;
      this.date = date;
      this.time = time;
      this.cityFrom = cityFrom;
      this.cityTo = cityTo;
      this.status = status;
   }
   public Trip(){
   }

   protected Trip(Parcel in) {
      tripID = in.readString();
      name = in.readString();
      date = in.readString();
      time = in.readString();
      cityFrom = in.readString();
      cityTo = in.readString();
      status = in.readString();
   }

   public static final Creator<Trip> CREATOR = new Creator<Trip>() {
      @Override
      public Trip createFromParcel(Parcel in) {
         return new Trip(in);
      }

      @Override
      public Trip[] newArray(int size) {
         return new Trip[size];
      }
   };

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getTime() {
      return time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public String getCityFrom() {
      return cityFrom;
   }

   public void setCityFrom(String cityFrom) {
      this.cityFrom = cityFrom;
   }

   public String getCityTo() {
      return cityTo;
   }

   public void setCityTo(String cityTo) {
      this.cityTo = cityTo;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getTripID() {
      return tripID;
   }

   public void setTripID(String tripID) {
      this.tripID = tripID;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(@NonNull Parcel parcel, int i) {
      parcel.writeString(tripID);
      parcel.writeString(name);
      parcel.writeString(date);
      parcel.writeString(time);
      parcel.writeString(cityFrom);
      parcel.writeString(cityTo);
      parcel.writeString(status);
   }
}
