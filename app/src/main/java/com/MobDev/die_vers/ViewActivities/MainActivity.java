package com.MobDev.die_vers.ViewActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.MobDev.die_vers.DomainClasses.User;
import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewFragments.AddPostFragment;
import com.MobDev.die_vers.ViewFragments.PostDetailFragment;
import com.MobDev.die_vers.ViewFragments.PostFragment;
import com.MobDev.die_vers.ViewFragments.ProfileFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    //UI ELLEMENTS
    PostFragment postFragment;
    ProfileFragment profileFragment;
    AddPostFragment addPostFragment;
    PostDetailFragment newPostDetailFragment;
    ProfileFragment newProfileFragment;
    AddPostFragment newAddPostFragment;
    FragmentManager fragmentManager;
    SearchView searchView;
    BottomNavigationItemView homeItem;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            fragmentManager = getSupportFragmentManager();
            postFragment = new PostFragment();
            addPostFragment = new AddPostFragment();
            profileFragment = new ProfileFragment();
            setContentView(R.layout.activity_main);
            searchView = findViewById(R.id.search_bar);
            int searchCloseButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
            ImageView closeButton = (ImageView) searchView.findViewById(searchCloseButtonId);
            homeItem = findViewById(R.id.bottom_action_home);

            bottomNavigationView = findViewById(R.id.bottom_navigation);

            mAuth = FirebaseAuth.getInstance();
            if(mAuth.getCurrentUser() != null){
                final DocumentReference docRef = db.collection("Users").document(mAuth.getCurrentUser().getUid());
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                    }
                });
            }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_action_home) {
                    searchView.setQuery("", false);
                    searchView.setIconified(true);
                    postFragment.resetData();
                    swapPostFragment(postFragment);
                }
                if (item.getItemId() == R.id.bottom_action_profile) {
                    searchView.setQuery("", false);
                    searchView.setIconified(true);
                    swapProfileFragment(profileFragment);
                }
                if (item.getItemId() == R.id.bottom_action_add_post) {
                    searchView.setQuery("", false);
                    searchView.setIconified(true);
                    swapAddPostFragment(addPostFragment);
                }
                return true;
            }
        });

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.setQuery("", false);
                    searchView.setIconified(true);
                    postFragment.resetData();
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    swapPostFragment(postFragment);
                    postFragment.searchData(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            swapPostFragment(postFragment);
    }

    public void swapPostFragment(PostFragment pf) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!pf.isAdded()) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.main_container, pf);
        }
        if (newPostDetailFragment != null) {
            if (newPostDetailFragment.isAdded()) {
                fragmentTransaction.remove(newPostDetailFragment);
            }
        }
        fragmentTransaction.commit();
    }
    public void swapProfileFragment(ProfileFragment pf) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!pf.isAdded()) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.main_container, pf);
        }
        if (newProfileFragment != null) {
            if (newProfileFragment.isAdded()) {
                fragmentTransaction.remove(newProfileFragment);
            }
        }
        fragmentTransaction.commit();
    }

    public void swapAddPostFragment(AddPostFragment pf) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!pf.isAdded()) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.main_container, pf);
        }
        if (newAddPostFragment != null) {
            if (newAddPostFragment.isAdded()) {
                fragmentTransaction.remove(newAddPostFragment);
            }
        }
        fragmentTransaction.commit();
    }
    public void swapDetailFragment(Bundle data) {
        newPostDetailFragment = PostDetailFragment.newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        newPostDetailFragment.setArguments(data);
        fragmentTransaction.add(R.id.main_container, newPostDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

