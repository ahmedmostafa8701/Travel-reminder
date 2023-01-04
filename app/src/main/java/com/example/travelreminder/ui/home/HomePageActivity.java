package com.example.travelreminder.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityHomePageBinding;
import com.example.travelreminder.pojo.User;
import com.example.travelreminder.ui.AddTripActivity;
import com.example.travelreminder.ui.TripAdapter;
import com.example.travelreminder.ui.auth.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ActivityHomePageBinding binding;
    View header;
    HomeViewModel viewModel;
    TextView email;
    TextView username;
    ImageView imageView;
    TripAdapter adapter;
    RecyclerView recyclerView;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getUser();
        binding.fab.setOnClickListener(this);
    }

    void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout, binding.toolbar, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        header = binding.navigationView.getHeaderView(0);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new TripAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        binding.navigationView.setNavigationItemSelectedListener(this);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        email = header.findViewById(R.id.email_header);
        username = header.findViewById(R.id.user_name_header);
        imageView = header.findViewById(R.id.image_header);
    }

    private void getUser() {
        if(!viewModel.isAuth()){
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            finish();
        }
        viewModel.getUser().observe(this, (user -> {
            email.setText(user.getEmail());
            username.setText(user.getUserName());
            if(user.getImage() != null){
                imageView.setImageBitmap(user.getImage());
            }
            adapter.setTrips(user.getTrips());
        }));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.upcoming){

        }
        else if(item.getItemId() == R.id.history){

        }
        else if(item.getItemId() == R.id.history_map){

        }
        else if(item.getItemId() == R.id.sign_out){
            if(viewModel.isAuth()){
                viewModel.signOut();
            }
            startActivity(new Intent(this, LoginActivity.class));
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab){
            startActivity(new Intent(this, AddTripActivity.class));
        }
    }
}