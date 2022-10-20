package com.example.travelreminder.Auth;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpWithEmailAndPassword implements SignUp, OnCompleteListener<AuthResult> {
    private static SignUpWithEmailAndPassword instance;
    private SignUpWithEmailAndPassword(){

    }
    public static SignUpWithEmailAndPassword getInstance() {
        if(instance == null){
            instance = new SignUpWithEmailAndPassword();
        }
        return instance;
    }

    @Override
    public void register(String email, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this::onComplete);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){}
        else{}
    }
}
