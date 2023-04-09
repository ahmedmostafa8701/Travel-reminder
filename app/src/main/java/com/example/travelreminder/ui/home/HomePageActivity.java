package com.example.travelreminder.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelreminder.Constants;
import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityHomePageBinding;
import com.example.travelreminder.model.Trip;
import com.example.travelreminder.model.User;
import com.example.travelreminder.ui.addtrip.AddTripActivity;
import com.example.travelreminder.ui.login.LoginActivity;


public class HomePageActivity extends AppCompatActivity{
    ActivityHomePageBinding binding;
    View header;
    HomeViewModel viewModel;
    TextView email;
    TextView username;
    ImageView imageView;
    TripAdapter adapter;
    RecyclerView recyclerView;
    User homeUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getUser();
        binding.fab.setOnClickListener(view -> startActivity(new Intent(this, AddTripActivity.class)));
    }

    void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout, binding.toolbar, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        header = binding.navigationView.getHeaderView(0);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new TripAdapter(this, this::showMenu, new StartTrip() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void start(Trip trip) {
                String origin = trip.getCityFrom();
                String destination = trip.getCityTo();
                Uri directionsUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + origin + "&destination=" + destination);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, directionsUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        binding.navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        email = header.findViewById(R.id.email_header);
        username = header.findViewById(R.id.user_name_header);
        imageView = header.findViewById(R.id.image_header);
    }

    private void showMenu(int position) {
        PopupMenu popupMenu = new PopupMenu(this, recyclerView.getChildAt(position).findViewById(R.id.trip_menu));
        popupMenu.inflate(R.menu.trip_item_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true);
        }
        popupMenu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) item -> {
            if(item.getItemId() == R.id.delete_trip_menu){
                viewModel.removeTrip(homeUser.getTrips().get(position).getTripID());
            }
            else if(item.getItemId() == R.id.edit_trip_menu){
                move(AddTripActivity.class, homeUser.getTrips().get(position).getTripID());
            }
            return true;
        });
        popupMenu.show();
    }

    private void getUser() {
        if(!viewModel.isSignIn()){
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            finish();
        }
        viewModel.getUser().observe(this, user -> {
            homeUser = user;
            email.setText(user.getEmail());
            username.setText(user.getUserName());
            if(user.getImage() != null){
                imageView.setImageBitmap(user.getImage());
            }
            adapter.setTrips(user.getTrips());
        });
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.upcoming){

        }
        else if(item.getItemId() == R.id.history){

        }
        else if(item.getItemId() == R.id.history_map){

        }
        else if(item.getItemId() == R.id.sign_out){
            if(viewModel.isSignIn()){
                viewModel.signOut();
            }
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return true;
    }
    public void move(Class<?> cls, String tripID) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Constants.TRIP_ID_PASSING, tripID);
        startActivity(intent);
    }
}