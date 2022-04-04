package com.example.mraema.MedicineReminder.utiles;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;


public class AnimateCalendarBehavior extends AppBarLayout.Behavior {

    public AnimateCalendarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return false;  // super.onInterceptTouchEvent(parent, child, ev);
    }
}
