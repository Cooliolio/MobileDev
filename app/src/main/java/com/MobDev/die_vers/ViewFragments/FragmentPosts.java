package com.MobDev.die_vers.ViewFragments;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MobDev.die_vers.Adapters.PostAdapter;
import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.DomainClasses.User;
import com.MobDev.die_vers.R;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentPosts extends Fragment {

    RecyclerView rvPosts;
    PostAdapter adapter;
    View view;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Post> posts = new ArrayList<>();

    public FragmentPosts() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MOCK S
        Location locatie1 = new Location("gps");
        locatie1.setLatitude(50.930691);
        locatie1.setLongitude(5.332480);
        posts.add(new Post("test1fsdqfsqdfdqsfqsqs", 0.2, new ArrayList<String>(Arrays.asList("https://picsum.photos/id/325/200/200","url2")), locatie1));
        posts.add(new Post("test2", 0.2, new ArrayList<String>(Arrays.asList("https://picsum.photos/id/325/200/200","url2")), locatie1));
        posts.add(new Post("test3", 0.2, new ArrayList<String>(Arrays.asList("https://picsum.photos/id/325/200/200","url2")), locatie1));
        posts.add(new Post("test4", 0.2, new ArrayList<String>(Arrays.asList("https://picsum.photos/id/325/200/200","url2")), locatie1));
        User user = new User(1,"Furkan");
        //MOCK E
    }

    public void initRv(View view){
        rvPosts = view.findViewById(R.id.rv_posts);
        rvPosts.setHasFixedSize(true);
        adapter = new PostAdapter(posts,this);
        rvPosts.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(view.getContext(),1);
        rvPosts.setLayoutManager(gridLayoutManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_posts, container, false);
        initRv(this.view);
        return view;
    }
    public void changeLayout(){
        adapter.changeGrid();
        if(gridLayoutManager.getSpanCount() == 1){
            gridLayoutManager.setSpanCount(2);
        }else{
            gridLayoutManager.setSpanCount(1);
        }
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
    }
}
