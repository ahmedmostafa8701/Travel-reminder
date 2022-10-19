package com.example.travelreminder.Auth;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

class SignUpWithEmailAndPassword implements SignUp, OnCompleteListener<AuthResult> {
    FirebaseAuth auth;
    private static SignUpWithEmailAndPassword instance;
    private SignUpWithEmailAndPassword(){
        instance = new SignUpWithEmailAndPassword();
        auth = FirebaseAuth.getInstance();
    }
    public static SignUpWithEmailAndPassword getInstance() {
        return instance;
    }

    @Override
    public void register(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this::onComplete);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            FirebaseUser user = auth.getCurrentUser();
        }
        else{

        }
    }
}
