package com.MobDev.die_vers.ViewActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.MobDev.die_vers.Adapters.rvPostAdapter;
import com.MobDev.die_vers.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //DEBUG
    private static final String TAG = "MainActivity";

    //UI elements
    private BottomNavigationView bottomNavigationView;

    //RV
    private String mTitle;
    private String mDistance;
    private String mPrice;
    private ArrayList<String> mImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Firebase Connected", Toast.LENGTH_LONG).show();
        initImagesBitmaps();




        bottomNavigationView = findViewById(R.id.main_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.bottom_action_add_post:
                        overridePendingTransition(0, 0);
                        toActivity(new NewPostActivity());
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottom_action_notifications:
                        overridePendingTransition(0, 0);
                        toActivity(new NewPostActivity());
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottom_action_profile:
                        overridePendingTransition(0, 0);
                        toActivity(new NewPostActivity());
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.bottom_action_home:
                        finish();
                        overridePendingTransition(0, 0);
                        toActivity(new MainActivity());
                        overridePendingTransition(0, 0);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    private void toActivity(Activity a) {
        startActivity(new Intent(this, a.getClass()));
    }

    private void initImagesBitmaps(){
        Log.d(TAG,"InitImages");
        mImages.add("https://picsum.photos/id/325/200/200");
        mTitle = "test";
        mDistance = "test";
        mPrice = "test";
        initRv();
    }
    private void initRv(){
        Log.d(TAG,"InitRV");
        RecyclerView recyclerView = findViewById(R.id.rv_posts);
        rvPostAdapter adapter = new rvPostAdapter(mTitle,mDistance,mPrice,mImages,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
