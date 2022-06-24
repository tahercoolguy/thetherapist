package com.master.design.blackeye.views;
import android.content.Context;

import com.master.design.blackeye.R;
 import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;


public class RedColorDecorator implements DayViewDecorator {

    private ArrayList<CalendarDay> date;
    Context contextl;
    public RedColorDecorator(Context context, ArrayList<CalendarDay> dates) {

        contextl=context;
        date=dates;
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        try {
            view.setBackgroundDrawable(contextl.getDrawable(R.drawable.padding_gradient_calendar));
        }catch (Exception e)
        {

        }
    }



}