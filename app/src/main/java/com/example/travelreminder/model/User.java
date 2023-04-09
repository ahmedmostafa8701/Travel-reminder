package com.example.travelreminder.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class User{
   private String email;
   private String userName;
   private String phone;
   private Bitmap image;
   private List<Trip> trips;

   public User(String email, String userName, String phone, List<Trip> trips) {
      this.email = email;
      this.userName = userName;
      this.phone = phone;
      this.trips = trips;
   }
   public User(String email, String userName, String phone) {
      this.email = email;
      this.userName = userName;
      this.phone = phone;
      trips = new ArrayList<>();
   }
   public User(){
      trips = new ArrayList<>();
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public Bitmap getImage() {
      return image;
   }

   public void setImage(Bitmap image) {
      this.image = image;
   }

   public List<Trip> getTrips() {
      return trips;
   }
   public void setTrips(List<Trip> trips) {
      this.trips = trips;
   }
   public void addTrip(Trip trip){
      trips.add(trip);
   }
   public Trip getTrip(String tripID){
      for (Trip trip : trips) {
         if(trip.getTripID().equals(tripID)){
            return trip;
         }
      }
      return null;
   }
   public void updateTrip(String tripID, Trip update){
      Trip trip = getTrip(tripID);
      if(trip != null){
         trips.set(trips.indexOf(trip), update);
      }
   }
   public void removeTrip(String tripID){
      Trip trip = getTrip(tripID);
      if (trip != null) {
         trips.remove(trip);
      }
   }
}
