package com.MobDev.die_vers.ViewActivities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.MobDev.die_vers.Adapters.rvPostAdapter;
import com.MobDev.die_vers.Calculators.DistanceCalculator;
import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.DomainClasses.User;
import com.MobDev.die_vers.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //DEBUG
    private static final String TAG = "MainActivity";

    //UI elements
    private BottomNavigationView bottomNavigationView;

    //RV
    private ArrayList<Post> posts = new ArrayList<>();

    //Location
    private LocationManager locationManager;
    DistanceCalculator calculator;
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            int i = 0;
            for (Post p: posts) {
                posts.get(i).setPost_distance(calculator.calculateDistance(location, p.getLocation()));
                i++;
                initRv();
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //MOCK DATA START
        Location locatie1 = new Location("gps");
        locatie1.setLatitude(50.930691);
        locatie1.setLongitude(5.332480);
        posts.add(new Post("test1", 0.2, new ArrayList<String>(Arrays.asList("https://picsum.photos/id/325/200/200","url2")), locatie1));
        posts.add(new Post("test2", 0.2, new ArrayList<String>(Arrays.asList("https://picsum.photos/id/325/200/200","url2")), locatie1));
        posts.add(new Post("test3", 0.2, new ArrayList<String>(Arrays.asList("https://picsum.photos/id/325/200/200","url2")), locatie1));
        posts.add(new Post("test4", 0.2, new ArrayList<String>(Arrays.asList("https://picsum.photos/id/325/200/200","url2")), locatie1));
        User user = new User(1,"Furkan");
        //MOCK DATA END
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvName = findViewById(R.id.tv_name);
        tvName.append(user.getName());
        //Distance products
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationListener.onProviderDisabled(LocationManager.GPS_PROVIDER);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                    1, locationListener);
        }
        //Initialize Data
        initRv();
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

                        //overridePendingTransition(0, 0);
                        //toActivity(new NewPostActivity());
                        //overridePendingTransition(0, 0);
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

    private void initRv(){
        Log.d(TAG,"InitRV");
        RecyclerView recyclerView = findViewById(R.id.rv_posts);
        rvPostAdapter adapter = new rvPostAdapter(posts,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

