package com.MobDev.die_vers.Helpers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.MobDev.die_vers.DomainClasses.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class FirebaseDatabaseHelper {
    private static final String TITLE_KEY = "title";
    private static final String PRICE_KEY = "price";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGEURL_KEY = "imageUrl";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FirebaseDatabaseHelper() {
    }

    public void writePost(String title, double price, String description, ArrayList<String> imageUrls) {
        Map<String, Object> newPost = new HashMap<>();
        newPost.put(TITLE_KEY, title);
        newPost.put(PRICE_KEY, price);
        newPost.put(DESCRIPTION_KEY, description);
        newPost.put(IMAGEURL_KEY, imageUrls);


        //id is random
        db.collection("Posts").document().set(newPost);
    }
}
