package com.example.travelreminder.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<FirebaseUser> _fireUser = new MutableLiveData<>();
    public LoginViewModel() {
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
