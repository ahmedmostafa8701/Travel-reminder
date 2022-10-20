package com.example.travelreminder.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.travelreminder.R;
import com.example.travelreminder.databinding.ActivityHomePageBinding;
import com.example.travelreminder.pojo.UserModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.TwitterAuthCredential;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ActivityHomePageBinding binding;
    private View header;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);
        navIt();
        binding.fab.setOnClickListener(this);
    }
    void navIt(){
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout, binding.toolbar, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        header = binding.navigationView.getHeaderView(0);
        TextView userNameTxt = header.findViewById(R.id.user_name_header);
        userModel = UserModel.getInstance();
        userNameTxt.setText(userModel.getUserName());
        TextView email = header.findViewById(R.id.email_header);
        email.setText(userModel.getEmail());
        binding.navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.upcoming:

                break;
            case R.id.history:

                break;
            case R.id.history_map:

                break;
            case R.id.sign_up_button:

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

    }
}