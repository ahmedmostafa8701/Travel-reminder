package com.example.travelreminder.pojo.entities;

import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Trip {
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
}
