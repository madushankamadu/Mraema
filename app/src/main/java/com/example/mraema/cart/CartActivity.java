package com.example.mraema.cart;

import static com.example.mraema.MainActivity.sinhala;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mraema.R;
import com.example.mraema.selectMedicine.DirectOderFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "cartlistdebug";
    List<Items> itemArrayList;
    ActivityResultLauncher<String> mGetContent;
    private ListView list;
    private FirebaseUser user;
    private Button OrderCart, orderNowBtn;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private Button clearing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        list = findViewById(R.id.cartList);
        OrderCart = findViewById(R.id.order);
        itemArrayList  = new ArrayList<>();
        clearing = findViewById(R.id.emptyList);
        orderNowBtn = findViewById(R.id.order);


        setLanguage();

        ListAdapter listAdapter = new ListAdapter(this,R.layout.cart_list_item,itemArrayList);
        list.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView close = view.findViewById(R.id.close);
                TextView textName = view.findViewById(R.id.pillName);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(CartActivity.this, "close clicked", Toast.LENGTH_LONG).show();
                        String pilwanttoremove = textName.getText().toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("cart").child(pilwanttoremove);
                        ref.setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                list.invalidateViews();
                                itemArrayList.clear();
                                updateCartList();
                                listAdapter.notifyDataSetChanged();
                                Log.d(TAG, "onSuccess: achieved");
                            }
                        });
                    }
                });
            }
        });



        OrderCart.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                OrderNowFragment orderNowFragment = new OrderNowFragment();
                orderNowFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content, orderNowFragment).addToBackStack(null).commit();
                clearing.setVisibility(View.GONE);
                orderNowBtn.setVisibility(View.GONE);

               // Bundle bundle = new Bundle();

               // AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                DirectOderFragment directOderFragment = new DirectOderFragment();
//                directOderFragment.setArguments(bundle);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content, directOderFragment).addToBackStack(null).commit();


            }
        });

        clearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
                db.child("cart").setValue(null);
                itemArrayList.clear();
                ListAdapter adapter  = new ListAdapter(CartActivity.this,R.layout.cart_list_item,itemArrayList);

                list.setAdapter(adapter);
            }
        });


    }



    private void updateCartList() {
        itemArrayList.clear();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("cart").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Items items = new Items();
                        items.setPharmacyName(snapshot.child("pharmacyName").getValue().toString());
                        items.setCount(snapshot.child("units").getValue().toString());
                        items.setItemName(snapshot.child("nedicineName").getValue().toString());
                        items.setPharmacyUserId(snapshot.child("pharmacyUserId").getValue().toString());

                        itemArrayList.add(items);

                        ListAdapter adapter  = new ListAdapter(CartActivity.this,R.layout.cart_list_item,itemArrayList);

                        list.setAdapter(adapter);

                    }
                }else{

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartList();
    }


    public void orderNow(){
        Date currentTime = Calendar.getInstance().getTime();


        FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("oderHistory").child(String.valueOf(currentTime)).setValue(itemArrayList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("cart").setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        OrderCart.setBackgroundColor(ContextCompat.getColor(CartActivity.this, R.color.ash));
                        OrderCart.setText("ඇනවුම අහෝසි කරන්න.");

                    }
                });
            }
        });
    }

    private void choosePicture() {
        mGetContent.launch("image/*");
    }

    private void setLanguage(){
        if (sinhala == false){
            clearing.setText("Empty the cart");
            orderNowBtn.setText("Order Now");
        }else if(sinhala != true){
            clearing.setText("ඇනවුම හිස් කරන්න.");
            orderNowBtn.setText("අැනවුම් කරන්න.");
        }
    }
}


