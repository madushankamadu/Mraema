package com.example.mraema.MedicineReminder;

import static android.os.SystemClock.sleep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;

import com.example.mraema.MedicineReminder.db.MedicineDBHelper;

public class RecieverAlarm extends BroadcastReceiver {
    private static final String TAG = "alarmchecking";

    @Override
    public void onReceive(Context context, Intent intent) {

        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();
        sleep(30000);
        mediaPlayer.stop();
        MedicineDBHelper dbHelper = new MedicineDBHelper(context);
        dbHelper.getAlarmsWithTime(intent.getIntExtra("hour",0),intent.getIntExtra("minute",0));



    }
}
