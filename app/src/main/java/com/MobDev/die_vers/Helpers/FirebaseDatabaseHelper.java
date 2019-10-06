package com.MobDev.die_vers.Helpers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.MobDev.die_vers.DomainClasses.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class FirebaseDatabaseHelper {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Post> posts = new ArrayList<>();

    public interface DataStatus{
    }
    public FirebaseDatabaseHelper() {
    }

    public void readPosts(final DataStatus dataStatus){
        db.collection("Posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
}}
