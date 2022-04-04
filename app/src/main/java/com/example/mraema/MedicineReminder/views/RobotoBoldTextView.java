package com.example.mraema.MedicineReminder.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.mraema.MedicineReminder.utiles.FontUtil;


public class RobotoBoldTextView extends AppCompatTextView {

    public RobotoBoldTextView(Context context) {
        super(context);
        applyCustomFont();
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont();
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont();
    }

    private void applyCustomFont() {
        Typeface customFont = FontUtil.getTypeface(FontUtil.ROBOTO_BOLD);
        setTypeface(customFont);
    }
}