package com.example.travelreminder.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Trip {
   private String key;
   private String name;
   private String date;
   private String time;
   private String cityFrom;
   private String cityTo;
   private Status status;

   public Trip(String name, String date, String time, String cityFrom, String cityTo, Status status) {
      this.name = name;
      this.date = date;
      this.time = time;
      this.cityFrom = cityFrom;
      this.cityTo = cityTo;
      this.status = status;
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

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }
}
