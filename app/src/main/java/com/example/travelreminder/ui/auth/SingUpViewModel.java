package com.example.travelreminder.ui.auth;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.Auth.SignIn;
import com.example.travelreminder.Auth.SignInWithEmailAndPassword;
import com.example.travelreminder.Auth.SignUp;
import com.example.travelreminder.Auth.SignUpWithEmailAndPassword;
import com.example.travelreminder.pojo.User;
import com.example.travelreminder.pojo.database.RunTimeData;
import com.example.travelreminder.pojo.datalayer.IDatalayer;
import com.example.travelreminder.pojo.datalayer.remote.FirebaseDataLayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUpViewModel extends ViewModel {
    SignUp signUp;
    private MutableLiveData<FirebaseUser> _fireUser = new MutableLiveData<>();
    private LiveData<FirebaseUser> fireUser = _fireUser;
    IDatalayer datalayer;
    public SingUpViewModel() {
        signUp = new SignUpWithEmailAndPassword();
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