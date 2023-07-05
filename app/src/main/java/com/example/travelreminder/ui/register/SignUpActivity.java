package com.example.travelreminder.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelreminder.R;
import com.example.travelreminder.Validation;
import com.example.travelreminder.databinding.ActivitySignUpBinding;
import com.example.travelreminder.model.User;
import com.example.travelreminder.ui.main.MainActivity;
import com.example.travelreminder.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, Observer<FirebaseUser>, ActivityResultCallback<ActivityResult> {
    ActivitySignUpBinding binding;
    String email;
    String password;
    String rePassword;
    String phone;
    Bitmap image;
    String userName;
    SingUpViewModel viewModel;
    Validation validation;
    ActivityResultLauncher<Intent> activityResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel();
        init();
    }

    private void getViewModel() {
        viewModel = new ViewModelProvider(this).get(SingUpViewModel.class);
        viewModel.setContext(this);
        viewModel.getFireUser().observe(this, this);
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        binding.signUpButton.setOnClickListener(this);
        binding.alreadyRegisterButton.setOnClickListener(this);
        binding.uploadImageRegister.setOnClickListener(this);
        validation = new Validation();
        activityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this);
    }

    @Override
    public void onChanged(FirebaseUser firebaseUser) {
        if(firebaseUser != null){
            User user = new User(email, userName, phone);
            if(image != null){
                user.setImage(image);
            }
            viewModel.addUser(user);
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.signUpButton.getId()){
            userName = binding.userNameEnter.getText().toString();
            phone = binding.phoneEnter.getText().toString();
            email = binding.emailInput.getText().toString();
            password = binding.passwordInput.getText().toString();
            rePassword = binding.rePasswordEnter.getText().toString();
            signUp(email, password);
        }
        else if(v.getId() == binding.alreadyRegisterButton.getId()){
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else if(v.getId() == binding.uploadImageRegister.getId()){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResult.launch(intent);
        }
    }
    private void signUp(String email, String password) {
        if(validation.validateEmail(email) && password.equals(rePassword) && validation.validatePassword(password)){
            viewModel.signUp(email, password);
        }
    }
    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            assert data != null;
            Uri uri = data.getData();
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                binding.uploadImageRegister.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}