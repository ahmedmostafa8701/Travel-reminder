package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.travelreminder.Auth.SignIn;
import com.example.travelreminder.Auth.SignInWithEmailAndPassword;
import com.example.travelreminder.Auth.SyncAuth;
import com.example.travelreminder.Auth.UpdateUI;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityLoginUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements UpdateUI {
    SignIn singIn;
    ActivityLoginUserBinding binding;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intialize();
        validateUser();
    }

    private void intialize() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_user);
        binding.loginBtn.setOnClickListener((view)->logIn());
        binding.registerBtn.setOnClickListener((view)-> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });
    }

    private void validateUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(this, HomePageActivity.class));
        }
    }
    private void logIn() {
        if(validateData()){
            Toast.makeText(this, "please fill each field with valid data", Toast.LENGTH_SHORT).show();
            return;
        }
        SyncAuth syncAuth = new SyncAuth();
        syncAuth.setUpdateUI(this);
        singIn = new SignInWithEmailAndPassword(syncAuth);
        singIn.login(email, password);
    }
    private boolean validateData() {
        email = binding.emailInput.getText().toString();
        password = binding.passwordInput.getText().toString();
        if(email.contains(".com")
                && email.contains("@")
                && ((email.charAt(0) >= 'a' && email.charAt(0) <= 'z') || (email.charAt(0) >= 'A' && email.charAt(0) <= 'Z'))
                && password.length() < 6){
            return true;
        }
        return false;
    }
    @Override
    public void updateUI(FirebaseUser user) {
        if(user != null){
            startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
            finish();
        }
    }
}