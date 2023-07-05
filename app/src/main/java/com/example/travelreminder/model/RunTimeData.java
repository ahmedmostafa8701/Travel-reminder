package com.example.travelreminder.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public enum RunTimeData {
    instance;
    private MutableLiveData<User> _user = new MutableLiveData<>(new User());

    public LiveData<User> getUser() {
        return _user;
    }
    public void setUser(User user) {
        _user.postValue(user);
    }
    public void removeTrip(String tripID){
        User user = _user.getValue();
        user.removeTrip(tripID);
        setUser(user);
    }
    public void addTrip(Trip trip){
        User user = _user.getValue();
        user.addTrip(trip);
        setUser(user);
    }
    public void updateTrip(String tripID, Trip trip){
        User user = _user.getValue();
        user.updateTrip(tripID, trip);
        setUser(user);
    }
}
