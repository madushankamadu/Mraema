package com.example.mraema.cart;

import static com.example.mraema.MainActivity.sinhala;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mraema.R;
import com.example.mraema.authantication.MapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class OrderNowFragment extends Fragment {

    private static final String TAG = "onpharmacychecking";
    private Button uploadButton, oderMedicines;
    private EditText massage;
    ActivityResultLauncher<String> mGetContent;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ImageView doneIcon, pendingIcon;
    private FirebaseUser user;
    private String userName, key, imageKey;
    List<Items> itemArrayList;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView firstText,secondText;




    public OrderNowFragment() {
        // Required empty public constructor
    }


    //inflating fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_now, container, false);
        uploadButton = view.findViewById(R.id.upload_presc);
        doneIcon = view.findViewById(R.id.done_icon);
        pendingIcon = view.findViewById(R.id.pending_icon);
        oderMedicines = view.findViewById(R.id.orderBtn);
        firstText = view.findViewById(R.id.heading);
        secondText = view.findViewById(R.id.mapTopic);
        massage = view.findViewById(R.id.msg);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        user = FirebaseAuth.getInstance().getCurrentUser();

        //viewing map for getting delivery location
        Fragment fragment = new MapFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageUri = result;

            }
        });


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mGetContent.launch("image/*");

                }
        });

        oderMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (imageUri != null){
                    orderNow(view);
               // }else {
                 //   Toast.makeText(getActivity(), "තුන්ඩුව උඩුගත කිරීම අත්‍යවශ්‍යයි.", Toast.LENGTH_LONG).show();
                //}


            }
        });

        setLanguage();

        return view;
    }

    private void doSomeOperations() {
    }


    public void orderNow(View view){

        uploadImage(view);




        DatabaseReference referance = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());

        Items items = new Items();
        referance.child("name").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                userName = dataSnapshot.getValue().toString();
            }
        });

        referance.child("cart").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {


            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String pushKey = UUID.randomUUID().toString();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    itemArrayList = new ArrayList<>();


                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
                    String datetime = dateFormat.format(date);

                    items.setPharmacyName(snapshot.child("pharmacyName").getValue().toString());
                    items.setCount(snapshot.child("units").getValue().toString());
                    items.setItemName(snapshot.child("nedicineName").getValue().toString());
                    items.setPharmacyUserId(snapshot.child("pharmacyUserId").getValue().toString());
                    items.setTotalPrice(Integer.parseInt(snapshot.child("totalPricce").getValue().toString()));
                    String ids = snapshot.child("pharmacyUserId").getValue().toString();
                    Map<String,Object> map = new HashMap<>();
                    map.put(snapshot.child("pharmacyName").getKey(),snapshot.child("pharmacyName").getValue().toString());
                    map.put(snapshot.child("units").getKey(),snapshot.child("units").getValue().toString());
                    map.put(snapshot.child("nedicineName").getKey(),snapshot.child("nedicineName").getValue().toString());
                    map.put(snapshot.child("pharmacyUserId").getKey(),snapshot.child("pharmacyUserId").getValue().toString());
                    map.put(snapshot.child("totalPricce").getKey(),snapshot.child("totalPricce").getValue().toString());
                    map.put("date", datetime);
                    map.put("location",MapFragment.pharmacyLocation);
                    map.put("customer", user.getUid());
                    map.put("imgRef", imageKey);
                   // map.put("prescription",imageUri);
                    map.put("userName", userName);
                    map.put("accepted", "pending");


                    itemArrayList.add(items);


                    key = userName+" put an order on "+datetime;

                    FirebaseDatabase.getInstance().getReference().child("Pharmacy").child(ids).child("Notification").child(key).child(pushKey).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: "+ itemArrayList.toString()+" "+ids);
                            referance.child("cart").setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    itemArrayList.clear();
                                    map.clear();
                                    Toast.makeText(getActivity(), "ඔබ සාර්ථකව ඇනවුම් කිරීම සිදු කරන ලදි.", Toast.LENGTH_SHORT).show();
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(OrderNowFragment.this).commit();

                                }
                            });
                        }
                    });

                    FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("orderHistory").child(pushKey).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });


                }

            }

        });

    }

    private void uploadImage(View view) {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        String datetime = dateFormat.format(date);

        imageKey = String.valueOf(UUID.randomUUID());

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference sr = storageReference.child("images/"+imageKey);

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

    private void setLanguage(){
        if (sinhala == false) {
            firstText.setText("Please upload the prescription");
            secondText.setText("choose location to transport");
            massage.setHint("Any Comments");
            uploadButton.setText("Upload the prescription");
            oderMedicines.setText("Order Now");
        }else if (sinhala == true){
            firstText.setText("ඔබේ තුන්ඩුව ඉදිරිපත් කර ඖෂද ඇනවුම් කරන්න.!");
            secondText.setText("ඖෂද ගෙන්වාගත යුතු ස්ථානය ලකුණු කරන්න.");
            massage.setHint("අවශ්\u200Dය නම් වැඩිදුර තොරතුරු මෙහි යොදන්න. ");
            uploadButton.setText("තුන්ඩුව උඩුගත කරන්න.");
            oderMedicines.setText("ඇනවුම් කරන්න.");
        }
    }
}


