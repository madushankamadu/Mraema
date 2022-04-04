package com.example.mraema.MedicineReminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mraema.MedicineReminder.utiles.ActivityUtils;
import com.example.mraema.R;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MedicineActivity extends AppCompatActivity {

    CompactCalendarView mCompactCalendarView;
    TextView datePickerTextView;
    RelativeLayout datePickerButton;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    FrameLayout contentFrame;
    FloatingActionButton fabAddTask;
    CoordinatorLayout coordinatorLayout;
    ImageView arrow;

   // private MedicinePresenter presenter;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd", /*Locale.getDefault()*/Locale.ENGLISH);

    private boolean isExpanded = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        mCompactCalendarView = findViewById(R.id.compactcalendar_view);
        datePickerTextView = findViewById(R.id.date_picker_text_view);
        datePickerButton = findViewById(R.id.date_picker_button);
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = findViewById(R.id.app_bar_layout);
        contentFrame = findViewById(R.id.contentFrame);
        fabAddTask = findViewById(R.id.fab_add_task);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        arrow = findViewById(R.id.date_picker_arrow);

        setSupportActionBar(toolbar);

        mCompactCalendarView.setLocale(TimeZone.getDefault(),Locale.ENGLISH);
        mCompactCalendarView.setShouldDrawDaysHeader(true);

        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateClicked);

                int day = calendar.get(Calendar.DAY_OF_WEEK);

                if (isExpanded) {
                    ViewCompat.animate(arrow).rotation(0).start();
                } else {
                    ViewCompat.animate(arrow).rotation(180).start();
                }
                isExpanded = !isExpanded;
                appBarLayout.setExpanded(isExpanded, true);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });
        setCurrentDate(new Date());
        MedicineFragment medicineFragment = (MedicineFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (medicineFragment == null){
            medicineFragment = MedicineFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), medicineFragment, R.id.contentFrame);
        }

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded){
                    ViewCompat.animate(arrow).rotation(0).start();
                }else{
                    ViewCompat.animate(arrow).rotation(180).start();
                }

                isExpanded = !isExpanded;
                appBarLayout.setExpanded(isExpanded,true);
            }
        });


        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicineActivity.this,AddMedicineActivity.class);
                startActivity(intent);
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.report_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_stats){
            // here is the next step, Intent intent = new Intent(this,M)
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCurrentDate(Date date) {
        setSubtitle(dateFormat.format(date));
        mCompactCalendarView.setCurrentDate(date);
    }

    private void setSubtitle( String subtitle) {
        datePickerTextView.setText(subtitle);
    }

}
