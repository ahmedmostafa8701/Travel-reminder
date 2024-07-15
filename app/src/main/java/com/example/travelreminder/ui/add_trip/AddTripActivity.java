package com.example.travelreminder.ui.add_trip;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelreminder.Constants;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityAddTripBinding;
import com.example.travelreminder.datalayer.ISearchCity;
import com.example.travelreminder.datalayer.local.SearchCityLocal;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.Status;
import com.example.travelreminder.model.Trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {
    ActivityAddTripBinding binding;
    Trip trip;
    boolean editable;
    AddTripViewModel viewModel;
    ISearchCity searchCity;
    List<String> citiesFrom;
    List<String> citiesTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        getPassedData();
    }

    private void initialize() {
        viewModel = new ViewModelProvider(this).get(AddTripViewModel.class);
        viewModel.setContext(this);
        trip = new Trip();
        editable = false;
        searchCity = new SearchCityLocal(this);
        citiesFrom = new ArrayList<>();
        citiesTo = new ArrayList<>();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip);
        binding.arrivalDateAddTrip.setOnClickListener((view) -> datePicker());
        binding.arrivalTimeAddTrip.setOnClickListener((view) -> timePicker());
        binding.addTripBtn.setOnClickListener((view)-> addEditTrip());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        binding.fromAddTrip.setAdapter(adapter);
        binding.fromAddTrip.addTextChangedListener(new CityWatcher(this, list -> {
            adapter.clear();
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
            citiesFrom.clear();
            citiesFrom.addAll(list);
        }));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        binding.toAddTrip.setAdapter(adapter2);
        binding.toAddTrip.addTextChangedListener(new CityWatcher(this, list -> {
            adapter2.clear();
            adapter2.addAll(list);
            adapter2.notifyDataSetChanged();
            citiesTo.clear();
            citiesTo.addAll(list);
        }));
    }

    private void getPassedData() {
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null){
            String tripID = intent.getExtras().getString(Constants.TRIP_ID_PASSING, "");
            if(tripID.equals("")){
                return;
            }
            editable = true;
            binding.addTripBtn.setText(R.string.edit);
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
                (view, y, m, d) -> {
                    String date = String.format(Locale.getDefault(), "%04d/%02d/%02d", y, m + 1, d);
                    binding.arrivalDateAddTrip.setText(date);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void timePicker(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, h, m)-> {
                    String time = String.format(Locale.getDefault(), "%02d:%02d", h, m);
                    binding.arrivalTimeAddTrip.setText(time);
                },
                hour, minute, false);
        timePickerDialog.show();
    }

    private void addEditTrip() {
        trip.setCityFrom(binding.fromAddTrip.getText().toString());
        trip.setCityTo(binding.toAddTrip.getText().toString());
        trip.setDate(binding.arrivalDateAddTrip.getText().toString());
        trip.setTime(binding.arrivalTimeAddTrip.getText().toString());
        trip.setStatus(Status.Upcoming.toString());
        trip.setName(binding.tripNameAddTrip.getText().toString());
        if(!validate()){
            Toast.makeText(this, "Enter all data correct", Toast.LENGTH_SHORT).show();
            return;
        }
        if(editable){
            viewModel.updateTrip(trip.getTripID(), trip);
        }
        else{
            viewModel.addTrip(trip);
        }
        finish();
    }

    private boolean validate() {
        boolean match_city_from = false;
        boolean match_city_to = false;
        for (String s : citiesFrom) {
            if(s.equalsIgnoreCase(trip.getCityFrom())){
                match_city_from = true;
                trip.setCityFrom(s);
                break;
            }
        }
        for (String s : citiesTo) {
            if(s.equalsIgnoreCase(trip.getCityTo())){
                match_city_to = true;
                trip.setCityTo(s);
                break;
            }
        }
        return  !trip.getName().isEmpty() &&
                !trip.getDate().isEmpty() &&
                !trip.getTime().isEmpty() &&
                match_city_from && match_city_to;
    }
}