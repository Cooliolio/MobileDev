package com.MobDev.die_vers.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewFragments.FragmentPosts;
import com.MobDev.die_vers.ViewHolder.PostItemViewHolder;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostItemViewHolder>{

    private List<Post> posts;
    private List<String> keys;
    private FragmentPosts mContext;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private boolean bigSmalView = true;
    // True => BigLayout

    public PostAdapter(List<Post> posts, FragmentPosts mContext, List<String> keys) {
        this.posts = posts;
        this.mContext = mContext;
        this.keys = keys;
    }

    @Override
    public void onBindViewHolder(@NonNull PostItemViewHolder postViewHolder, int counter) {
        Glide.with(mContext)
                .asBitmap()
                .load(posts.get(counter).getImageUrls().get(0))
                .into(postViewHolder.postImage);
        postViewHolder.postTitle.setText(posts.get(counter).getPost_title());
        postViewHolder.postPrice.setText(posts.get(counter).getPrice() + "EUR");
        if(bigSmalView){
            postViewHolder.postDistance.setText(df2.format(posts.get(counter).getPost_distance()) + " KM");
        }
        postViewHolder.bind(posts.get(counter), keys.get(counter));
    }

    @NonNull
    @Override
    public PostItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if(bigSmalView){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item_big, viewGroup, false);
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item_small, viewGroup, false);
        }
        PostItemViewHolder holder = new PostItemViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
    public void changeGrid(){
        if(this.bigSmalView){
            bigSmalView = false;
        }else
        {
            bigSmalView = true;
        }
    }
}
