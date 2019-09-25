package com.MobDev.die_vers.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MobDev.die_vers.R;
import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class rvPostAdapter extends RecyclerView.Adapter<rvPostAdapter.ViewHolder>{

    private static final String TAG = "rvPostAdapter";

    private String mTitle;
    private String mDistance;
    private String mPrice;
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public rvPostAdapter(String mTitle, String mDistance, String mPrice, ArrayList<String> mImages, Context mContext) {
        this.mTitle = mTitle;
        this.mDistance = mDistance;
        this.mPrice = mPrice;
        this.mImages = mImages;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called.");//For debug

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(i))
                .into(viewHolder.postImage);

        viewHolder.postTitle.setText(mTitle);
        viewHolder.postPrice.setText(mPrice);
        viewHolder.postDistance.setText(mDistance);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on " + mTitle);
                Toast.makeText(mContext, mTitle,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages.size();
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
