package com.example.travelreminder.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.pojo.datalayer.Repo;
import com.example.travelreminder.pojo.entities.User;
import com.example.travelreminder.pojo.database.RunTimeData;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<User> _user;
    private LiveData<User> user;
    private Repo repo;
    public HomeViewModel() {
        this._user = new MutableLiveData<>();
        this.user = _user;
        this.repo = new Repo();
    }
    public void removeTrip(String tripID){
        repo.removeTrip(tripID);
    }
    public LiveData<User> getUser() {
        repo.getUser();
        return RunTimeData.instance.getUser();
    }

    public boolean isAuth(){
        return repo.isAuth();
    }

    public void signOut(){
        repo.signOut();
    }
}
