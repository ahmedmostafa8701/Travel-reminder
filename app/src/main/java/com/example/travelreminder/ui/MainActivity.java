package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.example.travelreminder.R;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String x = "ahmed";
        TextView txt = findViewById(R.id.textView7);
        txt.setText(x);
    }
}