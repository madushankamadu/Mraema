package com.example.mraema.MedicineReminder;



import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mraema.MedicineReminder.db.MedicineAlarm;
import com.example.mraema.MedicineReminder.db.MedicineDBHelper;
import com.example.mraema.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;



public class AddMedicineActivity extends AppCompatActivity {

    private static final String TAG = "isDataretrieved";
    TimePicker timePicker;
    private EditText editMedName, tvDoseQuantity;
    private AppCompatCheckBox everyDay;
    private CheckBox dvSunday, dvMonday, dvTuesday, dvWednesday, dvThursday, dvFriday, dvSaturday;
    private LinearLayout checkboxLayout;
    private TextView tvMedicineTime;
    private AppCompatSpinner spinnerDoseUnits;
    private int hour;
    private String minute;
    private List<String> doseUnitList;
    private String doseUnit;
    private List<MedicineAlarm> alarmList = new ArrayList<>();
    public static List<Long> l = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        editMedName = findViewById(R.id.edit_med_name);
        everyDay = findViewById(R.id.every_day);
        dvSunday = findViewById(R.id.dv_sunday);
        dvMonday = findViewById(R.id.dv_monday);
        dvTuesday = findViewById(R.id.dv_tuesday);
        dvWednesday = findViewById(R.id.dv_wednesday);
        dvThursday = findViewById(R.id.dv_thursday);
        dvFriday = findViewById(R.id.dv_friday);
        dvSaturday = findViewById(R.id.dv_saturday);
        checkboxLayout = findViewById(R.id.checkbox_layout);
        tvMedicineTime = findViewById(R.id.tv_medicine_time);
        tvDoseQuantity = findViewById(R.id.tv_dose_quantity);
        spinnerDoseUnits = findViewById(R.id.spinner_dose_units);



        setSpinnerDoseUnits();

        spinnerDoseUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (doseUnitList == null || doseUnitList.isEmpty()) {
                    return;
                }else
                    doseUnit = doseUnitList.get(i);
                Log.d("TAG", "checked "+doseUnit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        //getting the timepicker object
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        //attaching clicklistener on button
        findViewById(R.id.buttonAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getHour(), timePicker.getMinute(), 0);
                    hour = timePicker.getHour();
                    minute = String.valueOf(timePicker.getMinute());
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);

                    hour = timePicker.getCurrentHour();
                    minute = String.valueOf(timePicker.getCurrentMinute());

                }


                setAlarm(calendar.getTimeInMillis());



                MedicineAlarm alarm = new MedicineAlarm();

                if (editMedName.getText() != null){
                    alarm.setPilName(editMedName.getText().toString());
                    Log.d("TAG", "checked "+ alarm.getPilName());
                    alarm.setHour(hour);
                    Log.d("TAG", "checked "+ alarm.getHour());
                    alarm.setMinute(minute);
                    Log.d("TAG", "checked "+ alarm.getMinute());
                    alarm.setDoseQuantity(tvDoseQuantity.getText().toString());
                    Log.d("TAG", "checked "+ alarm.getDoseQuantity());
                    alarm.setDoseUnit(doseUnit);
                    Log.d("TAG", "checked "+ alarm.getDoseUnit());
                    alarm.setSunday(dvSunday.isChecked());
                    Log.d("TAG", "checked "+ alarm.getSunday());
                    alarm.setMonday(dvMonday.isChecked());
                    Log.d("TAG", "checked "+ alarm.getMonday());
                    alarm.setTuesday(dvTuesday.isChecked());
                    Log.d("TAG", "checked "+ alarm.getTuesday());
                    alarm.setWednsday(dvWednesday.isChecked());
                    Log.d("TAG", "checked "+ alarm.getWednsday());
                    alarm.setThursday(dvThursday.isChecked());
                    Log.d("TAG", "checked "+ alarm.getThursday());
                    alarm.setFriday(dvFriday.isChecked());
                    Log.d("TAG", "checked "+ alarm.getFriday());
                    alarm.setSaterday(dvSaturday.isChecked());
                    Log.d("TAG", "checked "+ alarm.getSaterday());
                    alarm.setEveryday(everyDay.isChecked());
                    Log.d("TAG", "checked "+ alarm.getEveryday());

                    MedicineDBHelper mDBHelper = new MedicineDBHelper(AddMedicineActivity.this);
                    mDBHelper.addAlarms(alarm);

                    MedicineDBHelper medicineDBHelper = new MedicineDBHelper(AddMedicineActivity.this);
                    alarmList = medicineDBHelper.getAlarms();


                }else{
                    Toast.makeText(AddMedicineActivity.this, "pill name required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//onctreate




    private long setTimeGapCalander( int hour, int minute) {
        //We need a calendar object to get the specified time in millis
        //as the alarm manager method takes time in millis to setup the alarm
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    hour, minute, 0);



        Log.d(TAG, "setAlarm: "+Calendar.YEAR+" "+Calendar.MONTH+" "+Calendar.DAY_OF_MONTH+" "+calendar.getTimeInMillis());
            return calendar.getTimeInMillis();
    }





    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, RecieverAlarm.class);
        i.putExtra("hour", hour);
        i.putExtra("minute", minute);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }





    private void setSpinnerDoseUnits() {
        doseUnitList = Arrays.asList(getResources().getStringArray(R.array.medications_shape_array));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(AddMedicineActivity.this), android.R.layout.simple_dropdown_item_1line, doseUnitList);
        spinnerDoseUnits.setAdapter(adapter);
    }



    private long setDatePicker(boolean sun, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat){
        int today = Calendar.DATE;
        int gap;
        long timeInMils=0;
        if (sun == true){
            gap = abs(Calendar.SUNDAY-today);
            timeInMils = gap*86400000;
        }
        if (mon == true){
            gap = abs(Calendar.MONDAY-today);
            timeInMils = gap*86400000;
        }
        if (tue == true){
            gap = abs(Calendar.TUESDAY-today);
            timeInMils = gap*86400000;
        }
        if (wed == true){
            gap = abs(Calendar.WEDNESDAY-today);
            timeInMils = gap*86400000;
        }
        if (thu == true){
            gap = abs(Calendar.THURSDAY-today);
            timeInMils = gap*86400000;
        }
        if (fri == true){
            gap = abs(Calendar.FRIDAY-today);
            timeInMils = gap*86400000;
        }
        if (sat == true){
            gap = abs(Calendar.SATURDAY-today);
            timeInMils = gap*86400000;
        }
        return timeInMils;
    }

}