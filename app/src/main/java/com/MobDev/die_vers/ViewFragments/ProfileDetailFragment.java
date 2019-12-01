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
import android.widget.TextView;

import com.MobDev.die_vers.DomainClasses.User;
import com.MobDev.die_vers.R;
import com.MobDev.die_vers.ViewActivities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class ProfileDetailFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private User user;
    private Context mcontext;
    private static SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy") ;
    FirebaseAuth mAuth;
    //UI
    TextView _name;
    TextView _signupdate;
    TextView _location;
    TextView _email;
    TextView _phone;
    View view;
    Button _logout;


    public ProfileDetailFragment() {
    }
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mcontext = this.getContext();
        this.view = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        _name = view.findViewById(R.id.tv_name_profile);
        _signupdate = view.findViewById(R.id.tv_signupdate_profile);
        _location = view.findViewById(R.id.tv_zipcode_profile);
        _email = view.findViewById(R.id.tv_email_profile);
        _phone = view.findViewById(R.id.tv_phone_profile);
        _logout = view.findViewById(R.id.btn_logout);

        _logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        final DocumentReference docRef = db.collection("Users").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user = document.toObject(User.class);
                        _name.setText(user.getName());
                        _signupdate.setText(df1.format(user.getSignupdate()));
                        _location.setText(user.getPostcode());
                        _email.setText(user.getEmail());
                        _phone.setText(user.getPhone());
                    }
                }
            }
        });
    }

    public static ProfileDetailFragment newInstance() {
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        return fragment;
    }
}
