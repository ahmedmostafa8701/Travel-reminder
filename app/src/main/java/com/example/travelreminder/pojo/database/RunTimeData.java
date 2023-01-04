package com.example.travelreminder.pojo.database;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travelreminder.pojo.User;

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
