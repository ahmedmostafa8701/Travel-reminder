package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityAddTripBinding;
import com.example.travelreminder.pojo.Constants;
import com.example.travelreminder.pojo.datalayer.Repo;
import com.example.travelreminder.pojo.entities.Status;
import com.example.travelreminder.pojo.entities.Trip;
import com.example.travelreminder.pojo.database.RunTimeData;

import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity {
    ActivityAddTripBinding binding;
    Trip trip;
    boolean editable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        getPassedData();
    }


    private void initialize() {
        trip = new Trip();
        editable = false;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip);
        binding.arrivalDateAddTrip.setOnClickListener((view) -> datePicker());
        binding.arrivalTimeAddTrip.setOnClickListener((view) -> timePicker());
        binding.addTripBtn.setOnClickListener((view)->addTrip());
    }
    private void getPassedData() {
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null){
            editable = true;
            binding.addTripBtn.setText("Edit");
            String tripID = intent.getExtras().getString(Constants.TRIP_ID_PASSING);
            trip = RunTimeData.instance.getUser().getValue().getTrip(tripID);
            binding.arrivalTimeAddTrip.setText(trip.getTime());
            binding.arrivalDateAddTrip.setText(trip.getDate());
            binding.toAddTrip.setText(trip.getCityTo());
            binding.fromAddTrip.setText(trip.getCityFrom());
            binding.tripNameAddTrip.setText(trip.getName());
        }
    }
    private void datePicker(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, 0,
                (view, y, m, d) -> binding.arrivalDateAddTrip.setText(d + "/"+ (m + 1) + "/" + y),
                year, month, day);
        datePickerDialog.show();
    }
    private void timePicker(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, h, m)-> binding.arrivalTimeAddTrip.setText(h  + ":" + m),
                hour, minute, false);
        timePickerDialog.show();
    }
    private void addTrip() {
        if(!validate()){
            Toast.makeText(this, "Enter all data correct", Toast.LENGTH_SHORT).show();
            return;
        }
        Repo repo = new Repo();
        trip.setCityFrom(binding.fromAddTrip.getText().toString());
        trip.setCityTo(binding.toAddTrip.getText().toString());
        trip.setDate(binding.arrivalDateAddTrip.getText().toString());
        trip.setTime(binding.arrivalTimeAddTrip.getText().toString());
        trip.setStatus(Status.Upcoming.toString());
        trip.setName(binding.tripNameAddTrip.getText().toString());
        if(editable){
            repo.updateTrip(trip.getTripID(), trip);
        }
        else{
            repo.addTrip(trip);
        }
        finish();
    }

    private boolean validate() {
        return !binding.tripNameAddTrip.getText().toString().equals("")
                && !binding.toAddTrip.getText().toString().equals("")
                && !binding.fromAddTrip.getText().toString().equals("")
                && !binding.arrivalDateAddTrip.getText().toString().equals("")
                && !binding.arrivalTimeAddTrip.getText().toString().equals("");
    }
}