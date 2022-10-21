package com.example.travelreminder.Auth;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpWithEmailAndPassword implements SignUp {
    SyncAuth syncAuth;

    public SignUpWithEmailAndPassword(SyncAuth syncAuth) {
        this.syncAuth = syncAuth;
    }

    @Override
    public void register(String email, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(syncAuth);
    }
}