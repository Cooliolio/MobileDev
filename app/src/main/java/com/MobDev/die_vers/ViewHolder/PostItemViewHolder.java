package com.MobDev.die_vers.ViewHolder;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.R;

public class PostItemViewHolder extends RecyclerView.ViewHolder{

    //Components
    public ImageView postImage;
    public TextView postTitle;
    public TextView postDistance;
    public TextView postPrice;

    private String key;
    public PostItemViewHolder(@NonNull View itemView) {
        super(itemView);
        postImage = itemView.findViewById(R.id.iv_post);
        postTitle = itemView.findViewById(R.id.tv_post_title);
        postPrice = itemView.findViewById(R.id.tv_post_price);
        postDistance = itemView.findViewById(R.id.tv_post_distance);
    }
    public void bind(Post post, String key){
        postImage.setImageURI(Uri.parse(post.getImageUrls().get(0)));
        postTitle.setText(post.getPost_title());
        postDistance.setText("20KM");
        postPrice.setText(""+post.getPrice());
    }
}
