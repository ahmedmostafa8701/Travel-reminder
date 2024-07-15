package com.example.travelreminder.ui.main;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class MainViewModel extends ViewModel {
    MutableLiveData<User> _user;
    LiveData<User> user;
    MainRepo mainRepo;

    public MainViewModel() {
        this._user = new MutableLiveData<>();
        this.user = _user;
    }
    public void setContext(Context context) {
        mainRepo = new MainRepo(context);
    }
    public void removeTrip(String tripID){
        mainRepo.removeTrip(tripID);
    }
    public LiveData<User> getUser() {
        mainRepo.loadUser();
        return RunTimeData.instance.getUser();
    }

    public boolean isSignIn(){
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        mainRepo.removeUserLocal();
    }
}
