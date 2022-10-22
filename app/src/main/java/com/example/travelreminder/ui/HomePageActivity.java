package com.example.travelreminder.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelreminder.Auth.SyncAuth;
import com.example.travelreminder.R;
import com.example.travelreminder.TripAdapter;
import com.example.travelreminder.databinding.ActivityHomePageBinding;
import com.example.travelreminder.FirebaseManager;
import com.example.travelreminder.pojo.Trip;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ActivityHomePageBinding binding;
    private View header;
    FirebaseUser user;
    private FirebaseManager firebaseManager;
    List<Trip> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);
        validateUser();
        navIt();
        showData();
        binding.fab.setOnClickListener(this);
    }

    private void showData() {
        binding.items.setLayoutManager(new LinearLayoutManager(this));
        binding.items.setAdapter(new TripAdapter(this, trips));
    }

    private void validateUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            finish();
        }
        else if(FirebaseManager.getInstance().getReference() == null){
            SyncAuth syncAuth = new SyncAuth();
            syncAuth.sync(user);
        }
    }

    void navIt(){
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout, binding.toolbar, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        header = binding.navigationView.getHeaderView(0);
        TextView userNameTxt = header.findViewById(R.id.user_name_header);
        firebaseManager = FirebaseManager.getInstance();
        userNameTxt.setText(firebaseManager.getUserName());
        TextView email = header.findViewById(R.id.email_header);
        email.setText(firebaseManager.getEmail());
        ImageView imageView = header.findViewById(R.id.image_header);
        imageView.setImageBitmap(firebaseManager.getProfileImage());
        binding.navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.upcoming:
                Toast.makeText(this, "Upcoming", Toast.LENGTH_SHORT).show();
                break;
            case R.id.history:

                break;
            case R.id.history_map:

                break;
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab){
            addTrip();
        }
    }

    private void addTrip() {
        startActivity(new Intent(this, AddTripActivity.class));
    }
}