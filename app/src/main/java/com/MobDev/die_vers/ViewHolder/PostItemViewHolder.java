package com.MobDev.die_vers.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MobDev.die_vers.Adapters.PostAdapter;
import com.MobDev.die_vers.R;

public class PostItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Components
    public ImageView postImage;
    public TextView postTitle;
    public TextView postDistance;
    public TextView postPrice;
    PostAdapter.OnPostListener onPostListener;

    public PostItemViewHolder(@NonNull View itemView, PostAdapter.OnPostListener onPostListener) {
        super(itemView);
        postImage = itemView.findViewById(R.id.iv_post);
        postTitle = itemView.findViewById(R.id.tv_post_title);
        postPrice = itemView.findViewById(R.id.tv_post_price);
        postDistance = itemView.findViewById(R.id.tv_post_distance);
        this.onPostListener = onPostListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onPostListener.onPostClick(getAdapterPosition());
    }
}
