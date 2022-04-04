package com.example.mraema.pharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.mraema.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class PharmacyHome extends AppCompatActivity {

    private static final String TAG = "checkthesnapshot";
    private Toolbar mToolbar;
    private RecyclerOrderAdapter recyclerOrderAdapter;
    private List<Orders> orderList;
    private RecyclerView recyclerOrderView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_home);
        orderList = new ArrayList<>();

        mToolbar = findViewById(R.id.pharmacy_home_toolbar);
        setSupportActionBar(mToolbar);

        recyclerOrderView = findViewById(R.id.recycler_order_view);

        user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child(user.getUid()).child("Notification");

        reference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Orders orders = new Orders();
                    orders.setUserName(ds.getKey());
                    
                    orderList.add(orders);
//                    reference.child(ds.getKey()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
//                        @Override
//                        public void onSuccess(DataSnapshot dataSnapshot) {
//                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
//                                snapshot.getKey();
//
//                                orders.setCustomerID(snapshot.child("customer").getValue().toString());
//                                orders.setPillName(snapshot.child("nedicineName").getValue().toString());
//                                orders.setPillPrice(snapshot.child("totalPricce").getValue().toString());
//                                orders.setPillQuantity(snapshot.child("units").getValue().toString());
//                                orders.setDateOfOrdered(snapshot.child("date").getValue().toString());
//                                orders.setUserName(snapshot.child("userName").getValue().toString());
//                                orderList.add(orders);
//
//                            }
//
//                        }
//
//                    });


                }
                setAdapter();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.pharmacy_home_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.profile:
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.content_pharmacy, PharmacyProfileFragment.class, null)
                        .commit();
                break;

            case R.id.addMedicines:
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.content_pharmacy, AddMedicneToStoreFragment.class, null)
                        .commit();
                break;
            case R.id.order_notification:
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    if (fragment != null) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        }


        return super.onOptionsItemSelected(item);
    }

    public void setAdapter() {

        recyclerOrderAdapter = new RecyclerOrderAdapter(orderList);
        recyclerOrderView.setLayoutManager(new LinearLayoutManager(PharmacyHome.this));
        recyclerOrderView.setAdapter(recyclerOrderAdapter);

        //loading.setVisibility(View.GONE);

    }


}
