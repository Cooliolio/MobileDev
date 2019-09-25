package com.MobDev.die_vers.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MobDev.die_vers.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public PostListAdapter(Cursor mCursor, Context mContext) {
        this.mCursor = mCursor;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PostListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.post_list_item, parent, false);
        return new PostListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListViewHolder holder, int position) {

        if(!mCursor.moveToPosition(position))return;
        String post_title = null;
        post_title = mCursor.getString(mCursor.getColumnIndex("postTitle"));
        holder.post_title.setText(post_title);
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    class PostListViewHolder extends RecyclerView.ViewHolder {

        ImageView post_img;
        TextView post_title;
        TextView post_distance;
        TextView post_price;

        public PostListViewHolder(View itemView) {
            super(itemView);

           // post_img = itemView.findViewById(R.id.iv_post);
            post_title = (TextView) itemView.findViewById(R.id.tv_post_title);
           // post_distance = (TextView) itemView.findViewById(R.id.tv_post_distance);
           // post_price = (TextView) itemView.findViewById(R.id.tv_post_price);

        }
    }
}

