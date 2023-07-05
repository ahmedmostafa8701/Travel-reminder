package com.example.travelreminder.ui.main;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.Auth.Auth;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.model.User;

public class MainViewModel extends ViewModel {
    MutableLiveData<User> _user;
    LiveData<User> user;
    MainRepo mainRepo;
    Auth auth;

    public MainViewModel() {
        this._user = new MutableLiveData<>();
        this.user = _user;
        auth = new Auth();
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
        return auth.isSignIn();
    }

    public void signOut(){
        auth.signOut();
        mainRepo.removeUserLocal();
    }
}
