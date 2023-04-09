package com.example.travelreminder.Auth;

import com.example.travelreminder.Auth.sign_in.SignIn;
import com.example.travelreminder.Auth.sign_up.SignUp;
import com.google.firebase.auth.FirebaseAuth;

public class Auth {

   public void login(String email, String password,SignIn signIn) {
      signIn.login(email, password);
   }

   public void register(String email, String password, SignUp signUp) {
      signUp.register(email, password);
   }

   public boolean isSignIn(){
      return FirebaseAuth.getInstance().getCurrentUser() != null;
   }

   public void signOut(){
      if(FirebaseAuth.getInstance().getCurrentUser() == null){
         return;
      }
      FirebaseAuth.getInstance().signOut();
   }
}
