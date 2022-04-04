package com.example.mraema.pharmacy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mraema.R;
import com.example.mraema.ocrReder.OcrActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class ViewOrderFragment extends Fragment {

    private static final String TAG = "iwanttocheckthis";
    private TextView medicineName, medicinePrice, medicineCount,totalPrice;
    private ListView orderedMedicineList;
    private ImageView prescription;
    private EditText replyComment;
    private Button acceptBtn, rejectBtn, goToScanner;
    List<Pils> medicines;
    private String customer, imageRef;
    private boolean isImageFitToScreen;
    Bitmap rotatedBitmap, bmp;
    private String orderKey;
    private String customerKey;


    public ViewOrderFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = getArguments().getString("customer");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_order, container, false);
        medicineName = view.findViewById(R.id.medicine_name);
        medicineCount = view.findViewById(R.id.medicine_count);
        medicinePrice = view.findViewById(R.id.medicine_price);
        totalPrice = view.findViewById(R.id.totalPrice);
        orderedMedicineList = view.findViewById(R.id.item_list);
        prescription = view.findViewById(R.id.prescription);
        replyComment = view.findViewById(R.id.reply_comment);
        acceptBtn = view.findViewById(R.id.accept_order);
        rejectBtn = view.findViewById(R.id.reject_order);
        goToScanner = view.findViewById(R.id.scanner);

        medicines  = new ArrayList<>();



        updateOrderList();


        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("User").child(customerKey).child("orderHistory").child(orderKey).child("accepted").setValue("yes").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "accepted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





        goToScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OcrActivity.class);
                intent.putExtra("imgRef",imageRef);
                startActivity(intent);
            }
        });



//        PilListAdapter pilListAdapter = new PilListAdapter(getActivity(),R.layout.ordered_pill_item,medicines);
//        orderedMedicineList.setAdapter(pilListAdapter);
//        pilListAdapter.notifyDataSetChanged();


        return view;
    }

    private void acceptOrder(){
        Toast.makeText(getActivity(), "The order accepted...", Toast.LENGTH_SHORT).show();

    }

    private void getPicture(String customerimg) {


        StorageReference sr = FirebaseStorage.getInstance().getReference("images/"+customerimg);

        final long ONE_MEGABYTE = 1024 * 1024*5;
        sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Matrix matrix = new Matrix();

                matrix.postRotate(90);
                bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                prescription.setImageBitmap(rotatedBitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getActivity(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });

        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    prescription.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                    prescription.setAdjustViewBounds(true);
                }else{
                    isImageFitToScreen=true;
                    prescription.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                    prescription.setScaleType(ImageView.ScaleType.FIT_XY);

                    acceptBtn.setVisibility(View.GONE);
                    rejectBtn.setVisibility(View.GONE);
                    replyComment.setVisibility(View.GONE);
                    totalPrice.setVisibility(View.GONE);
                    goToScanner.setVisibility(View.VISIBLE);



                }


            }
        });
    }


    private void updateOrderList() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("Pharmacy").child(user.getUid()).child("Notification").child(customer).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onSuccess: "+dataSnapshot.getKey());
                if (dataSnapshot != null) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Pils pils = new Pils();
                        pils.setPillName(snapshot.child("nedicineName").getValue().toString());
                        customerKey = snapshot.child("customer").getValue().toString();
                        orderKey = snapshot.getKey();
                        Log.d(TAG, "onSuccess: "+snapshot.getKey());
                        pils.setPilCount(snapshot.child("units").getValue().toString());
                        pils.setPilPrice(Integer.parseInt(snapshot.child("totalPricce").getValue().toString()));
                        pils.setImageKey(snapshot.child("imgRef").getValue().toString());
                        imageRef = snapshot.child("imgRef").getValue().toString();


                        medicines.add(pils);
                        getPicture(pils.getImageKey());

                        PilListAdapter pilListAdapter = new PilListAdapter(getActivity(), R.layout.ordered_pill_item, medicines);

                        orderedMedicineList.setAdapter(pilListAdapter);



                    }
                } else {

                }
            }
        });
    }

}