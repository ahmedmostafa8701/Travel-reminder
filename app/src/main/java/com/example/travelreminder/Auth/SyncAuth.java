package com.example.travelreminder.Auth;

import androidx.annotation.NonNull;

import com.example.travelreminder.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SyncAuth implements OnCompleteListener {
    private UpdateUI update;

    public void setUpdateUI(UpdateUI update) {
        this.update = update;
    }

    @Override
    public void onComplete(@NonNull Task task) {
        update.updateUI(FirebaseAuth.getInstance().getCurrentUser());
    }
    public void sync(){
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(FirebaseManager.dataCollection).child(email);
        FirebaseManager.getInstance().setReference(reference);
    }
}