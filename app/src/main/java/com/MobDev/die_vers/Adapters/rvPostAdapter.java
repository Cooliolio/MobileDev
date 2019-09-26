package com.MobDev.die_vers.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class rvPostAdapter extends RecyclerView.Adapter<rvPostAdapter.ViewHolder>{

    private ArrayList<Post> posts;
    private Context mContext;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public rvPostAdapter(ArrayList<Post> posts, Context mContext) {
        this.posts = posts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item, viewGroup, false);
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
        viewHolder.postDistance.setText(df2.format(posts.get(counter).getPost_distance()) + " KM"); //BEREKENING ALGORITME IN THE FUTURE

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, posts.get(counter).getPost_title(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView postImage;
        TextView postTitle;
        TextView postDistance;
        TextView postPrice;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.iv_post);
            postTitle = itemView.findViewById(R.id.tv_post_title);
            postDistance = itemView.findViewById(R.id.tv_post_distance);
            postPrice = itemView.findViewById(R.id.tv_post_price);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
