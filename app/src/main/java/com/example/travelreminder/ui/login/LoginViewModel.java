package com.example.travelreminder.ui.login;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.Auth.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    Auth auth;
    LoginRepo repo;
    private MutableLiveData<FirebaseUser> _fireUser = new MutableLiveData<>();
    public LoginViewModel() {
        auth = new Auth();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            _fireUser.setValue(FirebaseAuth.getInstance().getCurrentUser());
        }
    }
    public void setContext(Context context){
        repo = new LoginRepo(context);
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
