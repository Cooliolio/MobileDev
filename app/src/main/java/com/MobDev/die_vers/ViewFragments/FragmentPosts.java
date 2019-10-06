package com.MobDev.die_vers.ViewFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MobDev.die_vers.Adapters.PostAdapter;
import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.Helpers.FirebaseDatabaseHelper;
import com.MobDev.die_vers.R;

import java.util.List;

public class FragmentPosts extends Fragment {

    RecyclerView rvPosts;
    PostAdapter adapter;
    View view;
    private GridLayoutManager gridLayoutManager;

    public FragmentPosts() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FirebaseDatabaseHelper().readPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_posts, container, false);
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

    public void setConfig(List<Post> posts, List<String> keys){
        this.adapter = new PostAdapter(posts,this, keys);
        rvPosts = view.findViewById(R.id.rv_posts);
        rvPosts.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(view.getContext(),1);
        rvPosts.setLayoutManager(gridLayoutManager);
        rvPosts.setAdapter(adapter);
    }
}
