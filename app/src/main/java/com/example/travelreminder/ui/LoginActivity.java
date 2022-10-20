package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.travelreminder.Auth.SignIn;
import com.example.travelreminder.Auth.SignInWithEmailAndPassword;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityLoginUserBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button loginbtn;
    SignIn singIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        intialize();
        validateUser();
    }

    private void intialize() {
        email = findViewById(R.id.EmailInput);
        password = findViewById(R.id.PasswordInput);
        loginbtn = findViewById(R.id.loginBtn);
        loginbtn.setOnClickListener((view)->logIn());
    }

    private void logIn() {
        singIn = SignInWithEmailAndPassword.getInstance();
        singIn.login(email.getText().toString(),
                password.getText().toString());
    }
    private void validateUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth != null){
            startActivity(new Intent(this, HomePageActivity.class));
        }
    }
}