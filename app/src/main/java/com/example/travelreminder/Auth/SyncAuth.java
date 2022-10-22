package com.example.travelreminder.Auth;

import androidx.annotation.NonNull;

import com.example.travelreminder.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public void sync(FirebaseUser user){;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://travelreminder-b45ee-default-rtdb.firebaseio.com/users").child(user.getUid());
        FirebaseManager.getInstance().setReference(reference);
    }
}