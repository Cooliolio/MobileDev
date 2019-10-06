package com.MobDev.die_vers.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewFragments.FragmentPosts;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private ArrayList<Post> posts;
    private FragmentPosts mContext;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private boolean bigSmalView = true;
    // True => BigLayout
    public PostAdapter(ArrayList<Post> posts, FragmentPosts mContext) {
        this.posts = posts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if(bigSmalView){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item_big, viewGroup, false);
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item_small, viewGroup, false);
        }
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int counter) {
        Glide.with(mContext)
                .asBitmap()
                .load(posts.get(counter).getImageUrls().get(0))
                .into(viewHolder.postImage);
        viewHolder.postTitle.setText(posts.get(counter).getPost_title());
        viewHolder.postPrice.setText(posts.get(counter).getPrice() + "EUR");

        if(bigSmalView){
            viewHolder.postDistance.setText(df2.format(posts.get(counter).getPost_distance()) + " KM");
        }
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Components
        ImageView postImage;
        TextView postTitle;
        TextView postDistance;
        TextView postPrice;
        LinearLayout parentLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.iv_post);
            postTitle = itemView.findViewById(R.id.tv_post_title);
            postPrice = itemView.findViewById(R.id.tv_post_price);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            if(bigSmalView){
                postDistance = itemView.findViewById(R.id.tv_post_distance);
            }
        }
    }

}
