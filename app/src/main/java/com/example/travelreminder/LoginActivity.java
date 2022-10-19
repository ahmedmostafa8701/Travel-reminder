package com.example.travelreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://travelreminder-b45ee-default-rtdb.firebaseio.com/");

    private TextView mEmailTxt;
    private TextView mPasswordTxt;
    private TextView mRegistrationView;
    private Button mLoginBtn;
    private String mEmail;
    private String mPassword;

    Intent mIntent;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        initializeComponent();
        goToRegisterPage();
        login();
    }

    private void initializeComponent() {
        mRegistrationView = findViewById(R.id.registerBtn);
        mEmailTxt = findViewById(R.id.EmailInput);
        mPasswordTxt = findViewById(R.id.PasswordInput);
        mLoginBtn = findViewById(R.id.loginBtn);
    }

    private void login() {
        mLoginBtn.setOnClickListener(v -> {
            getLoginData();
            if(mEmail.equals("")|| mPassword.equals("")){
                Toast.makeText(LoginActivity.this,"please fill all the fields",Toast.LENGTH_LONG).show();
            }else{
                mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                mIntent = new Intent(LoginActivity.this, RegisterationActivity.class);
                                startActivity(mIntent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong password or email ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void getLoginData() {
        mEmail = mEmailTxt.getText().toString().trim();
        mPassword = mPasswordTxt.getText().toString().trim();
    }


    private void goToRegisterPage() {
        mRegistrationView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,RegisterationActivity.class);
            startActivity(intent);
        });
    }

}