package com.example.travelreminder.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.adapters.ImageViewBindingAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.pojo.User;
import com.example.travelreminder.pojo.database.RunTimeData;
import com.example.travelreminder.pojo.datalayer.IDatalayer;
import com.example.travelreminder.pojo.datalayer.remote.FirebaseDataLayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<User> _user;
    private LiveData<User> user;
    private IDatalayer datalayer;
    public HomeViewModel() {
        this._user = new MutableLiveData<>();
        this.user = _user;
        this.datalayer = new FirebaseDataLayer();
    }

    public LiveData<User> getUser() {
        datalayer.getUser();
        return RunTimeData.instance.getUser();
    }

    public boolean isAuth(){
        return datalayer.isAuth();
    }

    public void signOut(){
        datalayer.signOut();
    }
}
