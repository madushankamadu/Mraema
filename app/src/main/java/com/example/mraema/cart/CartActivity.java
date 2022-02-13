package com.example.mraema.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.example.mraema.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    List<Items> itemArrayList;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        list = findViewById(R.id.cartList);
        itemArrayList  = new ArrayList<>();

        updateCartList();



        ListAdapter listAdapter = new ListAdapter(this,R.layout.cart_list_item,itemArrayList);
        list.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();


    }

    private void updateCartList() {



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
                        items.setPharmacyId(snapshot.child("pharmacyId").getValue().toString());


                        itemArrayList.add(items);

                        ListAdapter adapter  = new ListAdapter(CartActivity.this,R.layout.cart_list_item,itemArrayList);

                        list.setAdapter(adapter);






//                        map.put("pharmacyName", name);
//                        map.put("nedicineName",unitName);
//                        map.put("pharmacyId",id);
//                        map.put("units",count);
//                        map.put("totalPricce",total);
                    }


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
}
