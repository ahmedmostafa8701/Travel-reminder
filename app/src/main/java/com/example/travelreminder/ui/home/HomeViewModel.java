package com.example.travelreminder.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.Auth.Auth;
import com.example.travelreminder.model.User;
import com.example.travelreminder.model.RunTimeData;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<User> _user;
    private LiveData<User> user;
    private Repo repo;
    private Auth auth;
    public HomeViewModel() {
        this._user = new MutableLiveData<>();
        this.user = _user;
        this.repo = new Repo();
        auth = new Auth();
    }
    public void removeTrip(String tripID){
        repo.removeTrip(tripID);
    }
    public LiveData<User> getUser() {
        repo.getUser();
        return RunTimeData.instance.getUser();
    }

    public boolean isSignIn(){
        return auth.isSignIn();
    }

    public void signOut(){
        auth.signOut();
    }
}
