package com.example.travelreminder.Auth;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInWithEmailAndPassword implements SignIn, OnCompleteListener<AuthResult> {
    FirebaseAuth auth;
    private static SignInWithEmailAndPassword instance;
    private SignInWithEmailAndPassword(){
        instance = new SignInWithEmailAndPassword();
        auth = FirebaseAuth.getInstance();
    }
    public static SignInWithEmailAndPassword getInstance() {
        return instance;
    }

    @Override
    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this::onComplete);
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
