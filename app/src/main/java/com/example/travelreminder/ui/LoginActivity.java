package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.travelreminder.Auth.SignIn;
import com.example.travelreminder.Auth.SignInWithEmailAndPassword;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityLoginUserBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginUserBinding binding;
    SignIn singIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        intialize();
        validateUser();
    }

    private void intialize() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_user);
        binding.loginButton.setOnClickListener((view)->logIn());
    }

    private void logIn() {
        singIn = SignInWithEmailAndPassword.getInstance();
        singIn.login(binding.Email.getText().toString(),
                binding.Password.getText().toString());
    }
    private void validateUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth != null){
            startActivity(new Intent(this, HomePageActivity.class));
        }
    }
}