package com.MobDev.die_vers.ViewFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MobDev.die_vers.Adapters.PostAdapter;
import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.Helpers.FirebaseDatabaseHelper;
import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewActivities.MainActivity;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostFragment extends Fragment implements PostAdapter.OnPostListener{

    RecyclerView rvPosts;
    PostAdapter adapter = new PostAdapter(this, this);
    View view;
    GridLayoutManager gridLayoutManager;
    ImageView ivToggleView;
    private boolean togleImage = true;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Post> posts = new ArrayList<>();

    public PostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_posts, container, false);
        Query firstQuery = db.collection("Posts");
        firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            Post post = doc.getDocument().toObject(Post.class);
                            post.setId(doc.getDocument().getId());
                            posts.add(post);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        setConfig();
        gridListEvent();
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

    public void setConfig(){
        adapter.clear();
        adapter.setPosts(posts);
        rvPosts = view.findViewById(R.id.rv_posts);
        rvPosts.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(view.getContext(),1);
        rvPosts.setLayoutManager(gridLayoutManager);
        rvPosts.setAdapter(adapter);//HIER
    }

    public Post getSelectedPost(int position){
        return posts.get(position);
    }

    @Override
    public void onPostClick(int position) {
        Post post = this.getSelectedPost(position);
        Bundle data = new Bundle();
        data.putString("post_id", post.getId());
        ((MainActivity)getActivity()).swapFragment(data);
    }
    public void gridListEvent(){
        ivToggleView = view.findViewById(R.id.ivToggleView);
        ivToggleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayout();
                if (togleImage) {
                    ivToggleView.setImageResource(R.drawable.ic_toggle_list);
                    togleImage = false;
                } else {
                    ivToggleView.setImageResource(R.drawable.ic_toggle_grid);
                    togleImage = true;
                }
            }
        });
    }
}
