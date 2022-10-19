package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.travelreminder.Auth.*;
import android.os.Bundle;

import com.example.travelreminder.Auth.SignUp;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityMainBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    SignUp signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }
    void initialize(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUp = SignInWithEmailAndPassword.getInstance();
    }
}