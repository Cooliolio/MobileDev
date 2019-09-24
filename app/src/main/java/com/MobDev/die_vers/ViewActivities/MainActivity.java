package com.MobDev.die_vers.ViewActivities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.MobDev.die_vers.R;

public class MainActivity extends AppCompatActivity {

    //UI elements
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaration by id
        bottomNavigationView = findViewById(R.id.main_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.bottom_action_add_post:
                        toActivity(new NewPostActivity());
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottom_action_notifications:
                        toActivity(new NewPostActivity());
                        return true;

                    case R.id.bottom_action_profile:
                        toActivity(new NewPostActivity());
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
}
