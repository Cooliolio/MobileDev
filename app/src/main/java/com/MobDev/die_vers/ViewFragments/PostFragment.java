package com.MobDev.die_vers.ViewFragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MobDev.die_vers.Adapters.PostAdapter;
import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewActivities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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
        if(savedInstanceState == null) {
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
        }

        return view;
    }

    public void setConfig(){
        adapter.clear();
        adapter.setPosts(posts);
        rvPosts = view.findViewById(R.id.rv_posts);
        rvPosts.setHasFixedSize(true);
        int span = 1;
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            span = 2;
        }else {
            span  = 1;
        }
        gridLayoutManager = new GridLayoutManager(view.getContext(),span);
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
        ((MainActivity)getActivity()).swapDetailFragment(data);

    }

    public void resetData() {
        db.collection("Posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                posts.clear();
                for (DocumentSnapshot doc : task.getResult()) {
                    Post post = doc.toObject(Post.class);
                    post.setId(doc.getId());
                    posts.add(post);
                }
                rvPosts.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void searchData(String query) {
        db.collection("Posts").whereEqualTo("category", query.toLowerCase()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                posts.clear();
                for(DocumentSnapshot doc : task.getResult()){
                    Post post = doc.toObject(Post.class);
                    post.setId(doc.getId());
                    posts.add(post);
                }
                rvPosts.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public static PostFragment newInstance() {
        return new PostFragment();
    }

    public void profilePosts(String userId){
        db.collection("Posts").whereEqualTo("userId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                posts.clear();
                for(DocumentSnapshot doc : task.getResult()){
                    Post post = doc.toObject(Post.class);
                    post.setId(doc.getId());
                    posts.add(post);
                }
                rvPosts.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
