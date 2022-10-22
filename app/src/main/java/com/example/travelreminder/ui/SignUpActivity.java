package com.example.travelreminder.ui;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.travelreminder.Auth.SignUp;
import com.example.travelreminder.Auth.SignUpWithEmailAndPassword;
import com.example.travelreminder.Auth.SyncAuth;
import com.example.travelreminder.Auth.UpdateUI;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivitySignUpBinding;
import com.example.travelreminder.FirebaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpActivity extends AppCompatActivity implements UpdateUI {
    ActivitySignUpBinding binding;
    String email;
    String password;
    String rePassword;
    String phone;
    Bitmap image;
    String userName;
    SignUp signUp;
    SyncAuth syncAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validateAuth();
        initialize();
    }

    private void validateAuth() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(SignUpActivity.this, HomePageActivity.class));
        }
    }

    private void initialize() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        binding.getRoot().findViewById(R.id.sign_up_button).setOnClickListener((view)->register());
        binding.alreadyRegisterButton.setOnClickListener((view)-> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
        binding.uploadImageRegister.setOnClickListener((view)->uploadImage());
    }
    private void register() {
        userName = binding.userNameEnter.getText().toString();
        phone = binding.phoneEnter.getText().toString();
        email = binding.emailEnterReg.getText().toString();
        password = binding.passwordEnter.getText().toString();
        rePassword = binding.rePasswordEnter.getText().toString();
        if(validate()){
            syncAuth = new SyncAuth();
            syncAuth.setUpdateUI(this);
            signUp = new SignUpWithEmailAndPassword(syncAuth);
            signUp.register(email, password);
        }
        else{
            Toast.makeText(this, "please fill all fields with valid data", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validate() {
        if(email.contains(".com")
                && email.contains("@")
                && ((email.charAt(0) >= 'a' && email.charAt(0) <= 'z') || (email.charAt(0) >= 'A' && email.charAt(0) <= 'Z'))
                && password.length() > 6
                && password.equals(rePassword)
                &&userName.length() > 5
                &&phone.length() > 5){
            return true;
        }
        return false;
    }
    @Override
    public void updateUI(FirebaseUser user) {
        if(user != null){
            syncAuth.sync(user);
            FirebaseManager.getInstance().addUser(user, userName, image, phone);
            startActivity(new Intent(SignUpActivity.this, HomePageActivity.class));
            finish();
        }
    }
    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
            binding.uploadImageRegister.setImageBitmap(image);
        }
    }
}