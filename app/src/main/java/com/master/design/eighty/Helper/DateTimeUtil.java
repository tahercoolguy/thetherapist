package com.master.design.eighty.Helper;

import android.app.FragmentManager;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.isToday;


public class DateTimeUtil implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final int SIXTY_DAYS = 60;
    public static final long ONE_HOUR = 3600 * 1000;

    public final static int MAX = -1;
    public final static int MIN = 1;
    public final static int NORMAL = 0;


    public final static int DATETIME = 1;
    public final static int ASAP = 2;
    public final static int HALF_HOUR = 3;

    private static DateTimeUtil dateTimeUtil;
    private static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("dd MMMM, yyyy h:mm aa", Locale.ENGLISH);
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
    private static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("h.mm aa", Locale.ENGLISH);
    private static SimpleDateFormat simpleDateMonthFormat = new SimpleDateFormat("dd MMMM", Locale.ENGLISH);

    private OnSelectedListener onDateTimeSelection;

    private boolean enableMinuteSelection = true;
    private boolean is_min_constraint_enable = false, is_max_constraint_enable = false;
    private Calendar min_date, max_date;

    public static DateTimeUtil getInstance() {
        dateTimeUtil = new DateTimeUtil();
        return dateTimeUtil;
    }

    public DateTimeUtil setMin(long time) {
        this.is_min_constraint_enable = true;
        this.min_date = Calendar.getInstance();
        if (time != 0) {
            this.min_date.setTimeInMillis(time);
        }
        return dateTimeUtil;
    }

    public DateTimeUtil setMax(long time) {
        this.is_max_constraint_enable = true;
        this.max_date = Calendar.getInstance();
        if (time != 0) {
            max_date.setTimeInMillis(time);
        }
        return dateTimeUtil;
    }

    public DateTimeUtil setConstraint(int constraint, boolean is_enable) {
        if (constraint == MIN) {
            this.is_min_constraint_enable = is_enable;
            this.min_date = Calendar.getInstance();
        }
        if (constraint == MAX) {
            this.is_max_constraint_enable = is_enable;
            this.max_date = Calendar.getInstance();
        }
        return dateTimeUtil;
    }

    public DateTimeUtil setMinMaxDate(int number_of_days) {
        if (number_of_days > 0) {
            is_max_constraint_enable = true;
            max_date = Calendar.getInstance();
            max_date.add(Calendar.DATE, number_of_days);

            is_min_constraint_enable = true;
            min_date = Calendar.getInstance();
            min_date.add(Calendar.DATE, -number_of_days);
        }
        return dateTimeUtil;
    }


    public DateTimeUtil setConstraint(Calendar now, int constraint) {
        if (constraint == MIN) {
            this.is_min_constraint_enable = true;
            this.min_date = now;
        }
        if (constraint == MAX) {
            this.is_max_constraint_enable = true;
            this.max_date = now;
        }

        return dateTimeUtil;
    }


    public DateTimeUtil setTimeConstraint(Long time, int constraint) {
        if (constraint == MIN) {
            this.is_min_constraint_enable = true;
            this.min_date = Calendar.getInstance();
            this.min_date.setTimeInMillis(time);
            if (isToday(time)) {
                this.min_date.add(Calendar.HOUR, 2);
            }
        }
        if (constraint == MAX) {
            this.is_max_constraint_enable = true;
            this.max_date = Calendar.getInstance();
            this.max_date.setTimeInMillis(time);
        }
        return dateTimeUtil;
    }

    public DateTimeUtil setTimeConstraint(Long time, int constraint, boolean use_constraint) {
        if (constraint == MIN) {
            this.is_min_constraint_enable = true;
            this.min_date = Calendar.getInstance();
            this.min_date.setTimeInMillis(time);
            if (isToday(time)) {
                if (use_constraint) {
                    this.min_date.add(Calendar.HOUR, 2);
                }
            }
        }
        if (constraint == MAX) {
            this.is_max_constraint_enable = true;
            this.max_date = Calendar.getInstance();
            this.max_date.setTimeInMillis(time);
        }
        return dateTimeUtil;
    }


    public DateTimeUtil datePicker(FragmentManager fragmentManager) {
        if (min_date == null) {
            min_date = Calendar.getInstance();
        }
        Calendar current = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(DateTimeUtil.this, current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH));
        if (is_min_constraint_enable) {
            dpd.setMinDate(min_date);
        }
        if (is_max_constraint_enable) {
            dpd.setMaxDate(max_date);
        }
        dpd.show(fragmentManager, "date_picker");
        return this;
    }

    public void setOnDateTimeSelectionListener(OnSelectedListener onDateTimeSelection) {
        this.onDateTimeSelection = onDateTimeSelection;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        if (onDateTimeSelection != null) {
            onDateTimeSelection.getTime(calendar.getTimeInMillis());
        }
    }


    public DateTimeUtil timePicker(FragmentManager fragmentManager) {
        if (min_date == null) {
            min_date = Calendar.getInstance();
        }
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, min_date.get(Calendar.HOUR_OF_DAY), min_date.get(Calendar.MINUTE), false);
        tpd.enableMinutes(enableMinuteSelection);

        if (is_min_constraint_enable && isToday(min_date.getTimeInMillis())) {
            tpd.setMinTime(getTimePoint(min_date));
        }
        if (is_max_constraint_enable) {
            tpd.setMinTime(getTimePoint(min_date));
        }
        tpd.setThemeDark(false);
        tpd.vibrate(true);
        tpd.dismissOnPause(true);
        tpd.enableSeconds(false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.setTitle("Start Time");
        tpd.show(fragmentManager, "time_picker");
        return this;
    }

    private Timepoint getTimePoint(Calendar now) {
        return new Timepoint(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        if (enableMinuteSelection) {
            calendar.set(Calendar.MINUTE, minute);
        } else {
            calendar.set(Calendar.MINUTE, 0);
        }
        calendar.set(Calendar.SECOND, second);
        if (onDateTimeSelection != null) {
            onDateTimeSelection.getTime(calendar.getTimeInMillis());
        }
    }

    public static long getInSecond(long date) {
        return date / 1000;
    }


    public interface OnSelectedListener {
        void getTime(long time);

    }

    public static String getFormattedDate(long time) {
        Date date = new Date();
        date.setTime(time);
        return simpleDateFormat.format(date);
    }

    public static String getFormattedTime(long time) {
        Date date = new Date();
        date.setTime(time);
        return simpleTimeFormat.format(date);
    }

    public static String getFormattedDateMonth(long time) {
        Date date = new Date();
        date.setTime(time);
        return simpleDateMonthFormat.format(date);
    }

    public static String getFormattedTimeInHours(long time) {
        if (time <= 0) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.MINUTE, 0);
        return simpleTimeFormat.format(calendar.getTime());
    }

    public DateTimeUtil setEnableMinuteSelection(boolean enableMinuteSelection) {
        this.enableMinuteSelection = enableMinuteSelection;
        return dateTimeUtil;
    }

    public static String getFormattedDateTime(long selected_date, long time) {
        return getFormattedDate(selected_date) + " " + getFormattedTime(time);
    }


    public static String getFormattedDateTime(long selected_date, long start_time, long end_time) {
        return getFormattedDate(selected_date) + " " + getFormattedTimeInHours(start_time) + " - " + getFormattedTimeInHours(end_time);
    }


    public static String getFormattedDateTime(long time) {
        Date date = new Date();
        date.setTime(time);
        return simpleDateTimeFormat.format(date);
    }

    public static long getMergedTime(long selected_date, long time) {
        Calendar time_calendar = Calendar.getInstance();
        time_calendar.setTimeInMillis(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selected_date);
        calendar.set(Calendar.HOUR, time_calendar.get(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, time_calendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, time_calendar.get(Calendar.SECOND));

        return calendar.getTimeInMillis();
    }

    public static String getDate(long time) {
        Date date = new Date();
        date.setTime(time);
        return new SimpleDateFormat("dd", Locale.ENGLISH).format(date);
    }

    public static String getDay(long time) {
        Date date = new Date();
        date.setTime(time);
        return new SimpleDateFormat("EEE", Locale.ENGLISH).format(date);
    }

    public static String get12HourTime(long time) {
        Date date = new Date();
        date.setTime(time);
        return new SimpleDateFormat("h:mm aa", Locale.ENGLISH).format(date);
    }

    public static String getDayMonthYear(long time) {
        Date date = new Date();
        date.setTime(time);
        return new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(date);
    }

    public static String getAge(long millis) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTimeInMillis(millis);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return Integer.toString(age);
    }
}
