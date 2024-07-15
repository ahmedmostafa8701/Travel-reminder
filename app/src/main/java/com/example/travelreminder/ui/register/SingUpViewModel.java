package com.example.travelreminder.ui.register;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelreminder.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUpViewModel extends ViewModel {
    private MutableLiveData<FirebaseUser> _fireUser = new MutableLiveData<>();
    private LiveData<FirebaseUser> fireUser = _fireUser;
    SignUpRepo repo;
    public SingUpViewModel() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            _fireUser.setValue(FirebaseAuth.getInstance().getCurrentUser());
        }
    }
    public void setContext(Context context){
        repo = new SignUpRepo(context);
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
        repo.addUser(user);
    }
}