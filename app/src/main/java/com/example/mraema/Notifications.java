package com.example.mraema;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mraema.user.UserHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Notifications extends Service {
    private static final String TAG = "notificationchecking";
    private DatabaseReference referance;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        notification();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private void notification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            referance = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("orderHistory");
            referance.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d(TAG, "onChildAdded: added");
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d(TAG, "onChildChanged: changed");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);

                        NotificationManager manager = getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Notifications.this, "n")
                            .setContentText("New Data Is Added...")
                            .setSmallIcon(R.drawable.ic_notification)
                            .setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Notifications.this);
                    managerCompat.notify(999, builder.build());

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "onChildRemoved: removed");

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d(TAG, "onChildMoved: moved");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }else{

        }


    }

}
