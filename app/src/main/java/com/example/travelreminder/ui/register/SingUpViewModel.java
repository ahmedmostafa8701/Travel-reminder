package com.example.travelreminder.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.Auth.Auth;
import com.example.travelreminder.model.User;
import com.example.travelreminder.model.RunTimeData;
import com.example.travelreminder.datalayer.IDatalayer;
import com.example.travelreminder.datalayer.remote.FirebaseDataLayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUpViewModel extends ViewModel {
    Auth auth;
    private MutableLiveData<FirebaseUser> _fireUser = new MutableLiveData<>();
    private LiveData<FirebaseUser> fireUser = _fireUser;
    IDatalayer datalayer;
    public SingUpViewModel() {
        auth = new Auth();
        datalayer = new FirebaseDataLayer();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            _fireUser.setValue(FirebaseAuth.getInstance().getCurrentUser());
        }
    }

    public LiveData<FirebaseUser> getFireUser() {
        return fireUser;
    }

    public void signUp(String email, String password){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task)->{
            if(task.isSuccessful()){
                _fireUser.postValue(task.getResult().getUser());
            }
        });
    }
    public void addUser(User user) {
        RunTimeData.instance.setUser(user);
        datalayer.addUser(user);
    }
}