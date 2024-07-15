package com.example.travelreminder.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelreminder.Constants;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityMainBinding;
import com.example.travelreminder.databinding.NavHeaderBinding;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;
import com.example.travelreminder.services.StartTripService;
import com.example.travelreminder.ui.add_trip.AddTripActivity;
import com.example.travelreminder.ui.login.LoginActivity;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity{
    ActivityMainBinding binding;
    NavHeaderBinding headerBinding;
    MainViewModel viewModel;
    TripAdapter adapter;
    RecyclerView recyclerView;
    User homeUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getUser();
    }

    void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout, binding.toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        headerBinding = NavHeaderBinding.inflate(getLayoutInflater());
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        binding.navigationView.addHeaderView(headerBinding.getRoot());
        headerBinding.setUser(homeUser);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new TripAdapter(this, this::tripMenuItemSelected, this::startTrip);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        binding.navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.setContext(this);
        binding.fab.setOnClickListener(view -> startActivity(new Intent(this, AddTripActivity.class)));
    }
    private boolean tripMenuItemSelected(MenuItem item, int position) {
        if(item.getItemId() == R.id.delete_trip_menu){
            viewModel.removeTrip(homeUser.getTrips().get(position).getTripID());
        }
        else if(item.getItemId() == R.id.edit_trip_menu){
            move(AddTripActivity.class, homeUser.getTrips().get(position).getTripID());
        }
        return true;
    }
    private void startTrip(Trip trip){
        Intent intent = new Intent(this, StartTripService.class);
        Gson gson = new Gson();
        intent.putExtra(StartTripService.TRIP_START_KEY, gson.toJson(trip));
        startService(intent);
    }

    private void getUser() {
        if(!viewModel.isSignIn()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }
        viewModel.getUser().observe(this, user -> {
            homeUser = user;
            adapter.setTrips(user.getTrips());
        });
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upcoming:

                break;
            case R.id.history:

                break;
            case R.id.history_map:

                break;
            case R.id.sign_out:
                if (viewModel.isSignIn()) {
                    viewModel.signOut();
                }
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return true;
    }
    public void move(Class<?> cls, String tripID) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Constants.TRIP_ID_PASSING, tripID);
        startActivity(intent);
    }
}