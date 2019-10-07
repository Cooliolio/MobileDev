package com.MobDev.die_vers.Adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewFragments.FragmentPosts;
import com.MobDev.die_vers.ViewHolder.PostItemViewHolder;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static android.support.constraint.Constraints.TAG;

public class PostAdapter extends RecyclerView.Adapter<PostItemViewHolder> {

    private FragmentPosts mContext;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private boolean bigSmalView = true;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // True => BigLayout

    public PostAdapter(FragmentPosts mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull final PostItemViewHolder postItemViewHolder, final int position) {
        db.collection("Posts").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                PostItemViewHolder postViewHolder = postItemViewHolder;
              DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(position);
                    Post post = document.toObject(Post.class);
                    post.setId(document.getId());
                    postItemViewHolder.postTitle.setText(post.getTitle());

                    Glide.with(mContext)
                            .load(post.getImageUrls().get(0))
                            .into(postViewHolder.postImage);
                if(bigSmalView){
                    postViewHolder.postDistance.setText(df2.format(23.025155) + " KM");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @NonNull
    @Override
    public PostItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (bigSmalView) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item_big, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item_small, viewGroup, false);
        }
        PostItemViewHolder holder = new PostItemViewHolder(view);
        return holder;
    }

    public void changeGrid() {
        if (this.bigSmalView) {
            bigSmalView = false;
        } else {
            bigSmalView = true;
        }
    }

}
