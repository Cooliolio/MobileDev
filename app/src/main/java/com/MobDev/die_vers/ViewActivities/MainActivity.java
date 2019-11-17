package com.MobDev.die_vers.ViewActivities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;

import com.MobDev.die_vers.Adapters.PostAdapter;
import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewFragments.PostDetailFragment;
import com.MobDev.die_vers.ViewFragments.PostFragment;

public class MainActivity extends AppCompatActivity {

    //UI ELLEMENTS
    PostFragment postFragment;
    PostDetailFragment newPostDetailFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout fragmentContainer;
    SearchView searchView;
    BottomNavigationItemView homeItem;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            fragmentManager = getSupportFragmentManager();
            postFragment = new PostFragment();
            setContentView(R.layout.activity_main);
            searchView = findViewById(R.id.search_bar);
            int searchCloseButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
            ImageView closeButton = (ImageView) searchView.findViewById(searchCloseButtonId);
            homeItem = findViewById(R.id.bottom_action_home);

            bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_action_home) {
                    searchView.setQuery("", false);
                    searchView.setIconified(true);
                    postFragment.resetData();
                    swapPostFragment(postFragment);
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
                    swapPostFragment(postFragment);
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

