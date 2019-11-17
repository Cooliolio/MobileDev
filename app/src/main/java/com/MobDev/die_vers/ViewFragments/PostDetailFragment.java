package com.MobDev.die_vers.ViewFragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.R;
import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PostDetailFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Post selectedPost;
    private Context mcontext;
    private static SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy") ;
    private ArrayList<SlideModel> imageList;

    //UI
    ImageSlider image;
    TextView title;
    TextView price;
    TextView location;
    TextView date;
    TextView description;
    View view;

    public static PostDetailFragment newInstance() {
        return new PostDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mcontext = this.getContext();
        this.view = inflater.inflate(R.layout.post_detail_fragment, container, false);
        title = view.findViewById(R.id.tv_detail_title);
        price = view.findViewById(R.id.tv_detail_price);
        location = view.findViewById(R.id.tv_detail_location);
        date = view.findViewById(R.id.tv_detail_date);
        description = view.findViewById(R.id.tv_detail_description);
        image = view.findViewById(R.id.iv_detail_image);
        imageList = new ArrayList<SlideModel>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String post_id = getArguments().getString("post_id");
        final DocumentReference docRef = db.collection("Posts").document(post_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        selectedPost = document.toObject(Post.class);
                        for (String imageUrl:selectedPost.getImageUrls()) {
                            imageList.add(new SlideModel(imageUrl));
                        }
                        image.setImageList(imageList, true);
                        title.setText(selectedPost.getTitle());
                        price.setText("Prijs: € "+selectedPost.getPrice());
                        location.setText("Postcode: "+selectedPost.getPostcode());
                        date.setText("Datum: "+df1.format(selectedPost.getDate()));
                        description.setText(selectedPost.getDescription());
                    }
                }
            }
        });
    }

}
