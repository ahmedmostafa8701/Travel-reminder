package com.example.travelreminder.model;

public class Note {
   private String Content;
   private String key;
   public String getContent() {
      return Content;
   }

   public void upadteContent(String content) {
      Content = content;
   }

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }
}
