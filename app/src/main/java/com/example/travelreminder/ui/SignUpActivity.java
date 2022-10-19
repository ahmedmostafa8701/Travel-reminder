package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import com.example.travelreminder.Auth.*;
import android.os.Bundle;

import com.example.travelreminder.Auth.SignUp;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    SignUp signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        register();
    }
    void initialize(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUp = SignUpWithEmailAndPassword.getInstance();
    }
    void register(){


    }
    uploadImage(){

    }
}