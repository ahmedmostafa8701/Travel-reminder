package com.example.travelreminder;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class RegisterationActivity extends AppCompatActivity {
    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://travelreminder-b45ee-default-rtdb.firebaseio.com/");
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();
    private Uri mSelectedImageUri;

    private ImageView mImgUplode;
    private Button mSignUpBtn;
    private TextView mUserNameTxt;
    private TextView mPhoneTxt;
    private TextView mEmailTxt;
    private TextView mPasswordTxt;
    private TextView mPasswordConfirmationTxt;
    private String mUser;
    private String mPhone;
    private String mEmail;
    private String mPassword;
    private String mPasswordConfirm;
    private Button mAlreadyReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        initializeComponent();
        imageChooser();
        register();
        alreadyReg();
    }
    private void initializeComponent() {
        mImgUplode = findViewById(R.id.upload_image_register);
        mAlreadyReg = findViewById(R.id.already_register_button);
        mSignUpBtn = findViewById(R.id.sign_up_button);
        mUserNameTxt = findViewById(R.id.user_name_enter);
        mEmailTxt = findViewById(R.id.Email_text);
        mPhoneTxt = findViewById(R.id.phone_enter);
        mPasswordTxt = findViewById(R.id.password_enter);
        mPasswordConfirmationTxt = findViewById(R.id.re_password_enter);
    }

    private void alreadyReg(){
        mAlreadyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //prepare intent to open gallery
    private void imageChooser() {
        mImgUplode.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            launchSomeActivity.launch(i);
        });
    }
    private void register() {
        mSignUpBtn.setOnClickListener(v -> {
            getRegistrationData();
            if (checkValidData())
                Toast.makeText(RegisterationActivity.this, "please fill all fields", Toast.LENGTH_LONG).show();
            else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                mEmailTxt.setError("email is not valid");
                mEmailTxt.requestFocus();
            } else if (!mPassword.equals(mPasswordConfirm)) {
                mPasswordConfirmationTxt.setError("password not matched");
                mPasswordConfirmationTxt.requestFocus();
            } else if (mPassword.length() < 6) {
                mPasswordTxt.setError("password is short");
                mPasswordTxt.requestFocus();
            }
            else if (mPhone.length() < 11) {
                mPhoneTxt.setError("Invalid Phone Number");
                mPhoneTxt.requestFocus();
            }else {
                if (mSelectedImageUri == null)
                    Toast.makeText(RegisterationActivity.this, "please choose a photo", Toast.LENGTH_SHORT).show();
                else {
                    mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    uploadImg(task.getResult().getUser().getUid());
                                    setUserName(task.getResult().getUser().getUid());
                                    Toast.makeText(RegisterationActivity.this, "user registered successfully", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisterationActivity.this, "email is already registered",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private void getRegistrationData() {
        mPhone = mPhoneTxt.getText().toString().trim();
        mUser = mUserNameTxt.getText().toString().trim();
        mEmail = mEmailTxt.getText().toString().trim();
        mPassword = mPasswordTxt.getText().toString().trim();
        mPasswordConfirm = mPasswordConfirmationTxt.getText().toString().trim();
    }

    private void uploadImg(String imageName) {
        StorageReference imagesRef = mStorageReference.child("images/" + imageName);
        imagesRef.putFile(mSelectedImageUri).addOnFailureListener(e ->
                Toast.makeText(RegisterationActivity.this, "failed to upload photo", Toast.LENGTH_LONG).show());
    }

    private boolean checkValidData(){
        return mUser.isEmpty() || mEmail.isEmpty() || mPassword.isEmpty() || mPasswordConfirm.isEmpty() || mPhone.isEmpty();
    }
    private void setUserName(String UserId) {
        db.child("users");
        db.child(UserId).child("username").setValue(mUser).addOnFailureListener(e ->
                Toast.makeText(RegisterationActivity.this, "failed to set username", Toast.LENGTH_LONG).show());
    }

    //take photo from gallery
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null
                            && data.getData() != null) {
                        mSelectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    mSelectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap resized = Bitmap.createScaledBitmap(selectedImageBitmap,
                                (int) (mImgUplode.getWidth() * 0.8),
                                (int) (mImgUplode.getHeight() * 0.8), true);
                        mImgUplode.setImageBitmap(resized);
                    }
                }
            });
}
