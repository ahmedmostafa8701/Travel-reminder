package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityAddFireBinding;
import com.example.travelreminder.databinding.ActivityAddTripBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class addFire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddFireBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_add_fire);
        bind.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = bind.editTextTextPersonName2.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Map<String, String> map = new HashMap<>();
                map.put("hel", new String("h"));
                map.put("sdf", null);
                reference.child("users").child("ne").setValue(map);
            }
        });
    }
}