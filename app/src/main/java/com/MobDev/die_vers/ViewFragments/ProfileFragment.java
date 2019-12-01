package com.MobDev.die_vers.ViewFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.DomainClasses.User;
import com.MobDev.die_vers.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.adapters.ViewPagerAdapter;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    private TabLayout tabLayout;
    public ViewPager viewPager;
    public  View view;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_profile, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {


        private final List<String> mFragmentTitleList = new ArrayList<>();
        private  final PostFragment pf = PostFragment.newInstance();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            mFragmentTitleList.add("Profile");
            mFragmentTitleList.add("Posts");
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return ProfileDetailFragment.newInstance();
                case 1:
                    pf.profilePosts(mAuth.getUid());
                    return pf;
            }
            return  null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
