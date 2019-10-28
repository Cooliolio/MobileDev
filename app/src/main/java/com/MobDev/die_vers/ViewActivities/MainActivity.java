package com.MobDev.die_vers.ViewActivities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

