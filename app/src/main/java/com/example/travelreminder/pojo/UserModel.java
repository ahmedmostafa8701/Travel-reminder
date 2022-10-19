package com.example.travelreminder.pojo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

class UserModel {
    private static UserModel instance;
    FirebaseAuth auth;
    FirebaseUser user;
    private UserModel(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }
    public static UserModel getInstance(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null){
            return null;
        }
        if(instance == null){
            instance = new UserModel();
        }
        return instance;
    }

}
