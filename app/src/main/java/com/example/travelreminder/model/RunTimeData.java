package com.example.travelreminder.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public enum RunTimeData {
    instance;
    private MutableLiveData<User> _user = new MutableLiveData<>();
    RunTimeData() {
        _user.postValue(new User());
    }

    public LiveData<User> getUser() {
        return _user;
    }
    public void setUser(User user) {
        _user.postValue(user);
    }
}
