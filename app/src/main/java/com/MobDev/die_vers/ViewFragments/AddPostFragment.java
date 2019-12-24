package com.MobDev.die_vers.ViewFragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.MobDev.die_vers.DomainClasses.Post;
import com.MobDev.die_vers.DomainClasses.User;
import com.MobDev.die_vers.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class AddPostFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    User user;
    View view;
    Button btn_gallery;
    Button btn_camera;
    EditText title;
    EditText price;
    EditText description;
    GridView image_grid;
    ArrayList<Bitmap> images = new ArrayList<>();
    ImageAdapter imageAdapter;
    Button btn_save_post;
    ArrayList<String> imgs;
    StorageReference storageRef = storage.getReference();
    StorageReference postRefs;
    StorageReference postImagesRefs;
    String uniqueID;
    Spinner spinner;
    ArrayList<String> categories = new ArrayList<>();

    public AddPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_add_post, container, false);

        title = view.findViewById(R.id.et_addpost_title);
        price = view.findViewById(R.id.et_addpost_price);
        description = view.findViewById(R.id.et_addpost_description);

        btn_gallery = view.findViewById(R.id.btn_gallery);
        btn_camera = view.findViewById(R.id.btn_camera);
        image_grid = view.findViewById(R.id.images_grid);

        imageAdapter = new ImageAdapter(images);
        image_grid.setAdapter(imageAdapter);
        btn_save_post = view.findViewById(R.id.btn_save_post);

        spinner = view.findViewById(R.id.spnr_categories);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Query firstQuery = db.collection("Categories");
        firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            categories.add(doc.getDocument().get("name").toString());
                        }
                    }
                }
            }
        });

        btn_save_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save
                uniqueID = UUID.randomUUID().toString();
                imgs = new ArrayList<>();
                uploadImage(uniqueID);
                //add urls
                //post post
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                    Drawable d = new BitmapDrawable(getResources(), selectedImage);
                    images.add(selectedImage);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK && data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    if (selectedImage != null) {
                        Cursor cursor = getContext().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        if (cursor != null) {
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            Bitmap btm = BitmapFactory.decodeFile(picturePath);
                            images.add(btm);
                            cursor.close();
                        }
                    }
                }
                break;
        }
        initializeGrid();
    }

    public void initializeGrid() {
        for (Bitmap photo : images) {//or something like this
            ImageView image = new ImageView(getContext());
            image.setImageBitmap(photo);
        }
    }

    class ImageAdapter extends BaseAdapter {

        ArrayList<Bitmap> images;

        public ImageAdapter(ArrayList<Bitmap> images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Bitmap photo = this.images.get(position);
            ImageView image = new ImageView(getContext());
            image.setImageBitmap(photo);
            image.setMinimumHeight(200);

            image.setPadding(0, 0, 0, 0);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            return image;
        }
    }

    public void uploadImage(String uniqueID) {
        int counter = 0;
        for (Bitmap btmp : images) {
            postImagesRefs = storageRef.child(uniqueID + "/" + counter + ".jpg");
            counter++;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            btmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = postImagesRefs.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    postImagesRefs.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgs.add(uri.toString());
                            final DocumentReference docRef = db.collection("Users").document(mAuth.getUid());
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            user = document.toObject(User.class);
                                            Post post = new Post(title.getText().toString(), price.getText().toString(), new Date(), description.getText().toString(), imgs, mAuth.getUid(), user.getPostcode(), spinner.getSelectedItem().toString());
                                            db.collection("Posts").document().set(post);
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }

}
