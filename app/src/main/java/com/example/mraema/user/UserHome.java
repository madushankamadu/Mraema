package com.example.mraema.user;

import static com.example.mraema.user.distanceSetter.getKmFromLatLong;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mraema.R;
import com.example.mraema.authantication.LoginUser;
import com.example.mraema.cart.CartActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class UserHome extends AppCompatActivity {

    private static final String TAG = "itAccessed";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    int LOCATION_REQUEST_CODE = 10001;
    private DatabaseReference referance;
    private Location userLoc;
    private RecyclerView recyclerView;
    private List<Apharmacy> distanceToUser = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private ProgressBar loading;



    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location location:locationResult.getLocations()){

                    userLoc = location;
            }

            distanceToUser.clear();
            Log.d(TAG, "onLocationResult: databaseAccessed");
            databaseRef();
        }
    };// end of locationcallback


    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        setTitle(R.string.app_home);

        mToolbar = findViewById(R.id.user_home_toolbar);
        setSupportActionBar(mToolbar);

        recyclerView = findViewById(R.id.list);
        loading = findViewById(R.id.progresviewer);

       // Bundle user = getIntent().getExtras();
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "onCreate: user is  "  + user1.getUid());



        //Location getting time period.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }// onCreate method closed




// getting data about pharmacies
    private void databaseRef() {

        referance = FirebaseDatabase.getInstance().getReference("Pharmacy");
        Query query = FirebaseDatabase.getInstance().getReference("Pharmacy").orderByChild("email");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        for (DataSnapshot ds : snapshot.getChildren()){

                            double lat2  = Double.parseDouble(ds.child("locationLatitude").getValue().toString());
                            double lng2  = Double.parseDouble( ds.child("locationLongitude").getValue().toString());

                            double lat1 = location.getLatitude();
                            double lng1 = location.getLongitude();

                            Apharmacy apharmacy = new Apharmacy();
                             apharmacy.distance= getKmFromLatLong(lat1,lng1,lat2,lng2);
                             apharmacy.name = ds.child("PharmacyName").getValue().toString();
                             apharmacy.town = ds.child("Town").getValue().toString();
                             apharmacy.id = ds.child("regNumber").getValue().toString();

                            distanceToUser.add(apharmacy);//
                            Collections.sort(distanceToUser);//sort distances

                        }

                        //set the adapter for recyclerview
                        setAdapter();
                    }
                });

                fusedLocationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        checkSettingsAndStartLocationUpdates();
                        startLocationUpdates();
                    }
                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UserHome.this, "something happen wrong..!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setAdapter() {

        recyclerAdapter = new RecyclerAdapter(distanceToUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserHome.this));
        recyclerView.setAdapter(recyclerAdapter);

        loading.setVisibility(View.GONE);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_home_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent = new Intent(UserHome.this, LoginUser.class);
                startActivity(intent);

                break;
            case R.id.cart:
                Intent i = new Intent(UserHome.this, CartActivity.class);
                startActivity(i);
                break;
            case R.id.notification:
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.home_interface, NotificationFragment.class, null)
                        .commit();getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.home_interface, NotificationFragment.class, null)
                    .commit();
            break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.home_interface, ProfileFragment.class, null)
                        .commit();getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.home_interface, ProfileFragment.class, null)
                    .commit();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
        //    getLastLocation();
           // Log.d(TAG, "onStart: started");
            checkSettingsAndStartLocationUpdates();


        }else {
            askLocationPermission();
                    
        }
    }// onStart method closed

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }//onStop method Stoped

    //after  geting the permission

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
             //   getLastLocation();
                checkSettingsAndStartLocationUpdates();
                startLocationUpdates();
            }else{


            }
        }
    }
//get permission from user
    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
                
            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);

            }
        }
    }

    // check settings for updating realtime location
    private void  checkSettingsAndStartLocationUpdates(){

        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);

        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                startLocationUpdates();

            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (e instanceof ResolvableApiException){
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(UserHome.this, 1001);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                        checkSettingsAndStartLocationUpdates();
                    }
                }

            }
        });

    }// setting check closed


    // call start for location updating

    @SuppressLint("MissingPermission")
    private void startLocationUpdates(){

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper());

    }
    // call stop for location updating
    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

    }


}

