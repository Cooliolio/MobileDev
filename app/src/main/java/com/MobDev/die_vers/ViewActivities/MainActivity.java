package com.MobDev.die_vers.ViewActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewFragments.FragmentPosts;

public class MainActivity extends AppCompatActivity {

    //UI ELLEMENTS
    ImageView ivToggleView;
    FragmentPosts fragmentPosts;

    private boolean togleImage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentPosts = (FragmentPosts) getSupportFragmentManager().findFragmentById(R.id.main_container);

        ivToggleView = findViewById(R.id.ivToggleView);
        ivToggleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentPosts.changeLayout();
                if(togleImage){
                    ivToggleView.setImageResource(R.drawable.ic_toggle_list);
                    togleImage =false;
                }else{
                    ivToggleView.setImageResource(R.drawable.ic_toggle_grid);
                    togleImage = true;
                }
            }
        });
    }
}

