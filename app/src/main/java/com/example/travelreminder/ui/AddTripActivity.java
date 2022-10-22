package com.example.travelreminder.ui;

import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityAddTripBinding;
import com.example.travelreminder.FirebaseManager;
import com.example.travelreminder.pojo.Status;
import com.example.travelreminder.pojo.Trip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {
    ActivityAddTripBinding binding;
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;
    private int mMinute;
    private int mHour;
    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intialize();
        setDate(binding.arrivalDateAddTrip);
        setTime(binding.arrivalTimeAddTrip);
        setDate(binding.returnDateAddTrip);
        setTime(binding.returnTimeAddTrip);
    }

    private void intialize() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip);
        binding.addTripBtn.setOnClickListener((view)->addTrip());
    }
    private void setDate(EditText editText){
        mCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR,year);
                mCalendar.set(Calendar.MONTH,month);
                mCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                updateCalender();
            }

            private void updateCalender() {
                String format = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                editText.setText(sdf.format(mCalendar.getTime()));
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTripActivity.this,date,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void setTime(EditText editText){
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendar = Calendar.getInstance();
                int currentHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = mCalendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        String amPm;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        editText.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });
    }

    private void addTrip() {
        Trip trip = new Trip(binding.tripNameAddTrip.getText().toString(),
                binding.arrivalDateAddTrip.getText().toString(), binding.arrivalTimeAddTrip.getText().toString(),
                binding.fromAddTrip.getText().toString(), binding.toAddTrip.getText().toString(),
                Status.Upcoming);
        FirebaseManager.getInstance().addTrip(trip);
    }
}