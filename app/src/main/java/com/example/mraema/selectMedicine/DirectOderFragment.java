package com.example.mraema.selectMedicine;

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
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class DirectOderFragment extends Fragment {
    
    
    private Button uploadButton, oderMedicines;
    private EditText massage;
    ActivityResultLauncher<String> mGetContent;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ImageView doneIcon, pendingIcon;




    public DirectOderFragment() {
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
        View view = inflater.inflate(R.layout.fragment_direct_oder, container, false);
        uploadButton = view.findViewById(R.id.upload_presc);
        doneIcon = view.findViewById(R.id.doneIcon);
        pendingIcon = view.findViewById(R.id.PendingIcon);
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




    private void choosePicture() {
        mGetContent.launch("image/*");
    }
}

//rules_version = '2';
//        service firebase.storage {
//        match /b/{bucket}/o {
//        match /{allPaths=**} {
//        allow read, write: if false;
//        }
//        }
//        }