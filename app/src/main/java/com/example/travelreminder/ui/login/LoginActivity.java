package com.example.travelreminder.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelreminder.R;
import com.example.travelreminder.Validation;
import com.example.travelreminder.databinding.ActivityLoginUserBinding;
import com.example.travelreminder.ui.main.MainActivity;
import com.example.travelreminder.ui.register.SignUpActivity;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Observer<FirebaseUser> {

    ActivityLoginUserBinding binding;
    LoginViewModel viewModel;
    Validation validation;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getViewModel();
    }

    private void getViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.setContext(this);
        viewModel.getFireUser().observe(this, this);
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_user);
        binding.loginBtn.setOnClickListener(this);
        binding.register.setOnClickListener(this);
        validation = new Validation();
    }

    @Override
    public void onChanged(FirebaseUser firebaseUser) {
        if(firebaseUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.loginBtn.getId()){
            email = binding.emailInput.getText().toString();
            password = binding.passwordInput.getText().toString();
            login(email, password);
        }
        else if(v.getId() == binding.register.getId()){
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        }
    }

    private void login(String email, String password) {
        if(validation.validateEmail(email) && validation.validatePassword(password)){
            viewModel.login(email, password);
        }
    }
}