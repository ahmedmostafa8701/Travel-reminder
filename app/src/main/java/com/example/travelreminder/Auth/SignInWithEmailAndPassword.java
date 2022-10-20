package com.example.travelreminder.Auth;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInWithEmailAndPassword implements SignIn, OnCompleteListener<AuthResult> {
    private static SignInWithEmailAndPassword instance;
    private SignInWithEmailAndPassword(){
        instance = new SignInWithEmailAndPassword();
    }
    public static SignInWithEmailAndPassword getInstance() {
        if(instance == null){
            instance = new SignInWithEmailAndPassword();
        }
        return instance;
    }

    @Override
    public void login(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(this::onComplete);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){}
        else{}
    }
}
