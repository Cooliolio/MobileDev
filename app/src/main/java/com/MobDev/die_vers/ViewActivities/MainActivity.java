package com.MobDev.die_vers.ViewActivities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.MobDev.die_vers.Adapters.PostListAdapter;
import com.MobDev.die_vers.JSONArrayCursor;
import com.MobDev.die_vers.R;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    //UI elements
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private Cursor mCursor;

    private String sample_response = "[{\"postTitle\":test}]";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_posts);

        mCursor = getJSONCursor(sample_response);
        mAdapter = new PostListAdapter(mCursor,this);
        //Crashes HERE
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //recyclerView.setAdapter(mAdapter);

        //declaration by id
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

    private Cursor getJSONCursor(String sample_response) {
        try{

            JSONArray array = new JSONArray(sample_response);
            return new JSONArrayCursor(array);
        }catch (JSONException ex){
            String exception = ex.getMessage();
        }
        return null;
    }

    private void toActivity(Activity a) {
        startActivity(new Intent(this, a.getClass()));
    }
}
