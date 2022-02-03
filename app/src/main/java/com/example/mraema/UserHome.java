package com.example.mraema;

import static com.example.mraema.distanceSetter.getKmFromLatLong;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    int LOCATION_REQUEST_CODE = 10001;
    private DatabaseReference referance;
    private Location userLoc;
    private RecyclerView recyclerView;
    private List<Distances> distanceToUser = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;



    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location location:locationResult.getLocations()){

                    userLoc = location;
            }

            distanceToUser.clear();
            databaseRef();
        }
    };// end of locationcallback


    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mToolbar = findViewById(R.id.user_home_toolbar);
        setSupportActionBar(mToolbar);

        recyclerView = findViewById(R.id.list);


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

                            Distances distances = new Distances();
                             distances.distance= getKmFromLatLong(lat1,lng1,lat2,lng2);
                             distances.name = ds.child("PharmacyName").getValue().toString();
                             distances.town = ds.child("Town").getValue().toString();

                            distanceToUser.add(distances);//
                            Collections.sort(distanceToUser);//sort distances

                        }

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

            }
        });
    }

    private void setAdapter() {

        recyclerAdapter = new RecyclerAdapter(distanceToUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserHome.this));
        recyclerView.setAdapter(recyclerAdapter);

    }

    private void setUserInfo() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_home_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
        //    getLastLocation();
           // Log.d(TAG, "onStart: started");
            checkSettingsAndStartLocationUpdates();
            startLocationUpdates();

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

