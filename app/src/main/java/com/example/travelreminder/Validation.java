package com.example.travelreminder;

import android.util.Patterns;

public class Validation {
   public boolean validateEmail(String email){
      return  Patterns.EMAIL_ADDRESS.matcher(email).matches();
   }
   public boolean validatePassword(String password){
      return password.length() > 5;
   }
   public boolean validateUsername(String username){
      return username.length() > 5;
   }
   public boolean validatePhone(String phone){
      return Patterns.PHONE.matcher(phone).matches();
   }
}
