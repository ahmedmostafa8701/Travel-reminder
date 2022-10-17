package com.example.travelreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Any;

import java.util.HashMap;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        signUp = findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, login_user.class));
            }
        });
    }
}