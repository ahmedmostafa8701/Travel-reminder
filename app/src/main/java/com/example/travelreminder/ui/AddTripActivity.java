package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityAddTripBinding;
import com.example.travelreminder.FirebaseManager;
import com.example.travelreminder.pojo.Status;
import com.example.travelreminder.pojo.Trip;

public class AddTripActivity extends AppCompatActivity {
    ActivityAddTripBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intialize();
    }

    private void intialize() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip);
        binding.addTripBtn.setOnClickListener((view)->addTrip());
    }

    private void addTrip() {
        Trip trip = new Trip(binding.tripNameAddTrip.getText().toString(),
                binding.arrivalDateAddTrip.getText().toString(), binding.arrivalTimeAddTrip.getText().toString(),
                binding.fromAddTrip.getText().toString(), binding.toAddTrip.getText().toString(),
                Status.Upcoming);
        FirebaseManager.getInstance().addTrip(trip);
    }
}