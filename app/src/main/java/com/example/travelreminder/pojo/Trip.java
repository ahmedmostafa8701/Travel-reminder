package com.example.travelreminder.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class Trip {
   private String name;
   private LocalDate date;
   private LocalTime time;
   private String cityFrom;
   private String cityTo;
   private Status status;
   private List<String> notes;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public LocalDate getDate() {
      return date;
   }

   public void setDate(LocalDate date) {
      this.date = date;
   }

   public LocalTime getTime() {
      return time;
   }

   public void setTime(LocalTime time) {
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

   public List<String> getNotes() {
      return notes;
   }

   public void setNotes(List<String> notes) {
      this.notes = notes;
   }
}
