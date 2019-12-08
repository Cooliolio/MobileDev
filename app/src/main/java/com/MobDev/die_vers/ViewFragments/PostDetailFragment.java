package com.MobDev.die_vers.ViewFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.DomainClasses.User;
import com.MobDev.die_vers.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PostDetailFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private Post selectedPost;
    private User user;
    private Context mcontext;
    private static SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
    private ArrayList<SlideModel> imageList;

    //UI
    ImageSlider image;
    TextView title;
    TextView price;
    TextView location;
    TextView date;
    TextView description;
    TextView profilename;
    TextView profilenumber;
    TextView profileemail;
    ViewSwitcher switcher;
    View view;
    EditText et_price;
    EditText et_description;
    Button btn_save_changes;
    Button btn_delete_post;

    public static PostDetailFragment newInstance() {
        return new PostDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        mcontext = this.getContext();
        this.view = inflater.inflate(R.layout.post_detail_fragment, container, false);
        switcher = view.findViewById(R.id.vs_read_edit);
        et_price = view.findViewById(R.id.et_detail_price);
        et_description = view.findViewById(R.id.et_detail_description);


        title = view.findViewById(R.id.tv_detail_title);
        price = view.findViewById(R.id.tv_detail_price);
        location = view.findViewById(R.id.tv_detail_location);
        date = view.findViewById(R.id.tv_detail_date);
        description = view.findViewById(R.id.tv_detail_description);
        image = view.findViewById(R.id.iv_detail_image);
        profilename = view.findViewById(R.id.tv_detail_profilename);
        profilenumber = view.findViewById(R.id.tv_detail_profilenumber);
        profileemail = view.findViewById(R.id.tv_detail_profileemmail);
        btn_save_changes = view.findViewById(R.id.btn_save_changes);
        btn_delete_post = view.findViewById(R.id.btn_delete_post);

        final String post_id = getArguments().getString("post_id");
        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Posts").document(post_id).update(
                  "price",et_price.getText().toString(),
                  "description", et_description.getText().toString()
                );
            }
        });
        btn_delete_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Posts").document(post_id).delete();
                getActivity().getFragmentManager().popBackStack();
            }
        });


        profilenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + profilenumber.getText().toString());

                Intent i = new Intent(Intent.ACTION_DIAL, u);
                startActivity(i);
            }
        });
        imageList = new ArrayList<SlideModel>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String post_id = getArguments().getString("post_id");
        final DocumentReference postDoc = db.collection("Posts").document(post_id);

        postDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        selectedPost = document.toObject(Post.class);
                        for (String imageUrl : selectedPost.getImageUrls()) {
                            imageList.add(new SlideModel(imageUrl));
                        }
                        if(mAuth.getUid().equals(selectedPost.getUserId())){
                            switcher.showNext();
                        }
                        image.setImageList(imageList, true);
                        title.setText(selectedPost.getTitle());
                        price.setText("Prijs: € " + selectedPost.getPrice());
                        et_price.setText(selectedPost.getPrice());
                        location.setText("Postcode: " + selectedPost.getPostcode());
                        date.setText("Datum: " + df1.format(selectedPost.getDate()));
                        description.setText(selectedPost.getDescription());
                        et_description.setText(selectedPost.getDescription());

                        final DocumentReference profileDoc = db.collection("Users").document(selectedPost.getUserId());

                        profileDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        user = document.toObject(User.class);

                                        profilename.setText(user.getName());
                                        profileemail.setText(user.getEmail());
                                        profilenumber.setText(user.getPhone());
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

}
