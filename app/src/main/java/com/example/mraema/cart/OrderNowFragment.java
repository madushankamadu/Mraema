package com.example.mraema.cart;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mraema.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderNowFragment extends Fragment {

    private Button uploadButton, oderMedicines;
    private EditText massage;
    ActivityResultLauncher<String> mGetContent;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ImageView doneIcon, pendingIcon;
    private FirebaseUser user;
    List<Items> itemArrayList;



    public OrderNowFragment() {
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
        View view = inflater.inflate(R.layout.fragment_order_now, container, false);
        uploadButton = view.findViewById(R.id.upload_presc);
        doneIcon = view.findViewById(R.id.done_icon);
        pendingIcon = view.findViewById(R.id.pending_icon);
        oderMedicines = view.findViewById(R.id.orderBtn);
        itemArrayList = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageUri = result;
                uploadImage(view);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        oderMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderNow();
            }
        });

        return view;
    }

    private void uploadImage(View view) {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference sr = storageReference.child("images/"+randomKey);

        if (imageUri != null){

            sr.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    pendingIcon.setVisibility(View.GONE);
                    doneIcon.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Uploading Successful..! ", Toast.LENGTH_LONG).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Uploading faild..!", Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progressPercentage = (100.00*snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
                    pd.setMessage("Uploading  " + (int)progressPercentage+"%");
                }
            });
        }


    }

    public void orderNow(){


        DatabaseReference referance = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
        referance.child("cart").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Items items = new Items();
                    items.setPharmacyName(snapshot.child("pharmacyName").getValue().toString());
                    items.setCount(snapshot.child("units").getValue().toString());
                    items.setItemName(snapshot.child("nedicineName").getValue().toString());
                    items.setPharmacyId(snapshot.child("pharmacyId").getValue().toString());

                    itemArrayList.add(items);

                }
                Date currentTime = Calendar.getInstance().getTime();
                referance.child("oderHistory").child(String.valueOf(currentTime)).setValue(itemArrayList).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        referance.child("cart").setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                itemArrayList.clear();
                                Toast.makeText(getActivity(), "ඔබ සාර්ථකව ඇනවුම් කිරීම සිදු කරන ලදි.", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().beginTransaction().remove(OrderNowFragment.this).commit();

                            }
                        });
                    }
                });
            }

        });

    }




    private void choosePicture() {
        mGetContent.launch("image/*");
    }
}


