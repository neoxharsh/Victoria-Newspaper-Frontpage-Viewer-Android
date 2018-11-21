package com.neoxharsh.vicnews.vicnewsfp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

public class CalenderCustomDialog {
    private OnDateChangeListner onDateChangeListner;
    private Context ctx;
    private Dialog aDBuilder;

    public CalenderCustomDialog(Context ctx, OnDateChangeListner listner){
        this.ctx = ctx;
        this.onDateChangeListner = listner;
        createDialog();
    }

    private void createDialog(){
        LayoutInflater li = (LayoutInflater)ctx.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ConstraintLayout ll = (ConstraintLayout) li.inflate(R.layout.cv_dialog,null,false);
        CalendarView cv = (CalendarView) ll.getChildAt(0);
        Calendar minDate = new Calendar.Builder().setDate(1848, 9, 15).build();
        Calendar maxDate = new Calendar.Builder().setDate(1952, 2, 9).build();
        cv.setMinDate(minDate.getTimeInMillis());
        cv.setMaxDate(maxDate.getTimeInMillis());
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                onDateChangeListner.onDateSet(String.valueOf(year),String.valueOf(month),String.valueOf(dayOfMonth));
                aDBuilder.dismiss();
            }
        });

        aDBuilder = new AlertDialog.Builder(ctx).setView(ll).create();
    }
    public void showCalender(){
        aDBuilder.show();
    }

}
