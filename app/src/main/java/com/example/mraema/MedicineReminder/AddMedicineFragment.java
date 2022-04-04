//package com.example.mraema.MedicineReminder;
//
//import static android.content.Context.ALARM_SERVICE;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.widget.AppCompatCheckBox;
//import androidx.appcompat.widget.AppCompatSpinner;
//import androidx.fragment.app.Fragment;
//
//import com.example.mraema.MedicineReminder.db.MedicineAlarm;
//import com.example.mraema.MedicineReminder.db.MedicineDBHelper;
//import com.example.mraema.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.timepicker.MaterialTimePicker;
//import com.google.android.material.timepicker.TimeFormat;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Locale;
//import java.util.Objects;
//import java.util.Random;
//
//
//public class AddMedicineFragment extends Fragment {
//
//    private EditText editMedName, tvDoseQuantity;
//    private AppCompatCheckBox everyDay;
//    private CheckBox dvSunday, dvMonday, dvTuesday, dvWednesday, dvThursday, dvFriday, dvSaturday;
//    private LinearLayout checkboxLayout;
//    private TextView tvMedicineTime;
//   // private AppCompatSpinner spinnerDoseUnits;
//    private List<String> doseUnitList;
//    private boolean[] dayOfWeekList = new boolean[7];
//    private int hour, minute;
//    private String doseUnit;
//    private MaterialTimePicker picker;
//    private Context context;
//
//
//    public AddMedicineFragment() {
//        // Required empty public constructor
//    }
//
//
//
//    public static AddMedicineFragment newInstance(String param1, String param2) {
//        AddMedicineFragment fragment = new AddMedicineFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view;
//                //inflater.inflate(R.layout.fragment_add_medicine, container, false);
//        // Inflate the layout for this fragment
//        editMedName = view.findViewById(R.id.edit_med_name);
//        everyDay = view.findViewById(R.id.every_day);
//        dvSunday = view.findViewById(R.id.dv_sunday);
//        dvMonday = view.findViewById(R.id.dv_monday);
//        dvTuesday = view.findViewById(R.id.dv_tuesday);
//        dvWednesday = view.findViewById(R.id.dv_wednesday);
//        dvThursday = view.findViewById(R.id.dv_thursday);
//        dvFriday = view.findViewById(R.id.dv_friday);
//        dvSaturday = view.findViewById(R.id.dv_saturday);
//        checkboxLayout = view.findViewById(R.id.checkbox_layout);
//        tvMedicineTime = view.findViewById(R.id.tv_medicine_time);
//        tvDoseQuantity = view.findViewById(R.id.tv_dose_quantity);
//      ////  spinnerDoseUnits = view.findViewById(R.id.spinner_dose_units);
//
//        context = getActivity();
//
//
////        setSpinnerDoseUnits();
////
////        spinnerDoseUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                if (doseUnitList == null || doseUnitList.isEmpty()) {
////                    return;
////                }else
////                doseUnit = doseUnitList.get(i);
////                Log.d("TAG", "checked "+ doseUnit);
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> adapterView) {
////                return;
////            }
////        });
//
//        tvMedicineTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showTimePicker();
//            }
//        });
//
////        FloatingActionButton fab = requireActivity().findViewById(R.id.fab_done_task);
////        fab.setImageResource(R.drawable.ic_done);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                int checkBoxCounter = 0;
////
////                String pill_name = editMedName.getText().toString();
////                String doseQuantity = tvDoseQuantity.getText().toString();
////
////                Calendar takeTime = Calendar.getInstance();
////                Date date = takeTime.getTime();
////                String dateString = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date);
////
////                /** Updating model */
////                MedicineAlarm alarm = new MedicineAlarm();
////                int alarmId = new Random().nextInt(100);
////
////                /** If Pill does not already exist */
////                if (pill_name != "") {
////                    alarm.setPilName(pill_name);
////                    Log.d("TAG", "checked "+ pill_name);
////                    alarm.setDateString(dateString);
////                    alarm.setHour(hour);
////                    alarm.setMinute(minute);
////                    alarm.setDayOfWeek(dayOfWeekList);
////                    alarm.setDoseUnit(doseUnit);
////                    alarm.setDoseQuantity(doseQuantity);
////                    alarm.setAlarmId(alarmId);
////
////                    MedicineDBHelper mDBHelper = new MedicineDBHelper(getActivity());
////                    mDBHelper.addAlarms(alarm);
////
////                    Calendar calendar = Calendar.getInstance();
////                    if (android.os.Build.VERSION.SDK_INT >= 23) {
////                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
////                                picker.getHour(), picker.getMinute(), 0);
////                    }
////
////                    List<Long> listAlarm = new ArrayList();
////                    listAlarm.add(calendar.getTimeInMillis());
////                    Log.d("TAG", "checked "+ listAlarm);
////
////
////
////                    setAlarm(listAlarm);
////            }
////        }
////        });
//
//        return view;
//    }
//
//    private void showMedicineList() {
//
//    }
//
//    private void showTimePicker() {
//
//        picker = new MaterialTimePicker.Builder()
//                .setTimeFormat(TimeFormat.CLOCK_24H)
//                .setHour(12)
//                .setMinute(0)
//                .setTitleText("Select Alarm Time")
//                .build();
//        picker.show(getActivity().getSupportFragmentManager(), "myreminder");
//
//        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                tvMedicineTime.setText(picker.getHour() + " : " + picker.getMinute());
//
//            }
//
//        });
//
//    }
//
//    private void setAlarm(List<Long> alarmList) {
//
//        for (long time : alarmList){
//            Log.d("TAG", "checked "+time);
//            //getting the alarm manager
//            AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
//
//            //creating a new intent specifying the broadcast receiver
//            Intent i = new Intent(getActivity(), RecieverAlarm.class);
//
//            //creating a pending intent using the intent
//            PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, i, 0);
//
//            //setting the repeating alarm that will be fired every day
//            am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
//            Toast.makeText(getActivity(), "Alarm is set", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//
//
////    private void setSpinnerDoseUnits() {
////        doseUnitList = Arrays.asList(getResources().getStringArray(R.array.medications_shape_array));
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),android.R.layout.simple_dropdown_item_1line, doseUnitList);
////        spinnerDoseUnits.setAdapter(adapter);
////    }
//
//
//}