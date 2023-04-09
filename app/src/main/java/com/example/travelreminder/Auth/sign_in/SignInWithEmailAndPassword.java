package com.example.travelreminder.Auth.sign_in;


import com.google.firebase.auth.FirebaseAuth;

public class SignInWithEmailAndPassword implements SignIn {

    @Override
    public void login(String email, String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password);
    }
}
