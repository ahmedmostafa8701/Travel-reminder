package com.example.travelreminder.ui.addtrip;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.travelreminder.Constants;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityAddTripBinding;
import com.example.travelreminder.ui.home.Repo;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.Status;
import com.example.travelreminder.model.Trip;

import java.util.ArrayList;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        binding.fromAddTrip.setAdapter(adapter);
        binding.fromAddTrip.addTextChangedListener(new CityWatcher(this, adapter));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        binding.toAddTrip.setAdapter(adapter2);
        binding.toAddTrip.addTextChangedListener(new CityWatcher(this, adapter2));
    }
    private void getPassedData() {
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null){
            String tripID = intent.getExtras().getString(Constants.TRIP_ID_PASSING, "");
            if(tripID.equals("")){
                return;
            }
            editable = true;
            binding.addTripBtn.setText("Edit");
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