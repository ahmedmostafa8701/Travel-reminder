package com.example.travelreminder.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.Auth.SignIn;
import com.example.travelreminder.Auth.SignInWithEmailAndPassword;
import com.example.travelreminder.Validation;
import com.example.travelreminder.pojo.User;
import com.example.travelreminder.pojo.datalayer.remote.FirebaseDataLayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginViewModel extends ViewModel {
    SignIn signIn;
    private MutableLiveData<FirebaseUser> _fireUser = new MutableLiveData<>();
    public LoginViewModel() {
        signIn = new SignInWithEmailAndPassword();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            _fireUser.setValue(FirebaseAuth.getInstance().getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getFireUser() {
        return _fireUser;
    }

    public void login(String email, String password){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener((task)->{
            if(task.isSuccessful()){
                _fireUser.postValue(task.getResult().getUser());
            }
        });
    }
}
