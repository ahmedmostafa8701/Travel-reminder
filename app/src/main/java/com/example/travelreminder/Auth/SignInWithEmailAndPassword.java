package com.example.travelreminder.Auth;

import android.content.Context;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInWithEmailAndPassword implements SignIn {

    SyncAuth syncAuth;

    public SignInWithEmailAndPassword(SyncAuth syncAuth) {
        this.syncAuth = syncAuth;
    }
    @Override
    public void login(String email, String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(syncAuth);
    }
}
