package com.MobDev.die_vers.ViewActivities;

import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
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

public class MainActivity extends AppCompatActivity{

    //UI ELLEMENTS
    PostFragment postFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout fragmentContainer;
    SearchView searchView;
    BottomNavigationItemView homeItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
            searchView = findViewById(R.id.search_bar);
            int searchCloseButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);

        ImageView closeButton = (ImageView) searchView.findViewById(searchCloseButtonId);

        homeItem = findViewById(R.id.bottom_action_home);
        homeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentContainer = (FrameLayout) findViewById(R.id.main_container);
                postFragment = new PostFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.main_container, postFragment);
                fragmentTransaction.commit();
            }
        } );

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentContainer = (FrameLayout) findViewById(R.id.main_container);
                postFragment = new PostFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.main_container, postFragment);
                fragmentTransaction.commit();
                postFragment.resetData();
                searchView.setIconified(true);
            }
        });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    fragmentContainer = (FrameLayout) findViewById(R.id.main_container);
                    postFragment = new PostFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.main_container, postFragment);
                    fragmentTransaction.commit();
                    postFragment.searchData(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            fragmentContainer = (FrameLayout) findViewById(R.id.main_container);
            postFragment = new PostFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_container, postFragment);
            fragmentTransaction.commit();
    }

    public void swapFragment(Bundle data){
        PostDetailFragment newPostDetailFragment = PostDetailFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        newPostDetailFragment.setArguments(data);
        fragmentTransaction.replace(R.id.main_container, newPostDetailFragment);
        searchView.setIconified(true);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}

