package com.MobDev.die_vers.Adapters;

import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewFragments.PostFragment;
import com.MobDev.die_vers.ViewHolder.PostItemViewHolder;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostItemViewHolder> {

    private PostFragment mContext;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private static SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy") ;
    private boolean bigSmalView = true;


    private List<Post> posts = new ArrayList<>();
    private OnPostListener onPostListener;

    // True => BigLayout
    public PostAdapter(PostFragment mContext, OnPostListener onPostListener) {
        this.mContext = mContext;
        this.onPostListener = onPostListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final PostItemViewHolder postItemViewHolder, final int position) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(posts.get(position).getImageUrls().get(0))
                    .into(postItemViewHolder.postImage);
            postItemViewHolder.postTitle.setText(posts.get(position).getTitle());
            postItemViewHolder.postPrice.setText("€"+posts.get(position).getPrice());
            postItemViewHolder.postDate.setText(df1.format(posts.get(position).getDate()));
            postItemViewHolder.postDistance.setText(posts.get(position).getPostcode());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (bigSmalView) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item_big, viewGroup, false);
        }
        PostItemViewHolder holder = new PostItemViewHolder(view, onPostListener);
        return holder;
    }

    public void changeGrid() {
        if (this.bigSmalView) {
            bigSmalView = false;
        } else {
            bigSmalView = true;
        }
    }
    public interface OnPostListener{
        void onPostClick(int position);
    }
    public void clear(){
        this.posts.clear();
    }
}
