package com.example.travelreminder.Auth.sign_up;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpWithEmailAndPassword implements SignUp {

    @Override
    public void register(String email, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password);
    }
}