package com.lyl.calendar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lyl.calendar.utils.DateUtils;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private CalendarPickerView mCalendar;

    private int mStartHour, mStartMin, mEndHour, mEndMin;
    private int mAddCount;
    private Collection<Date> mSelectDate;
    private OverTimeDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDao = CalendarApplication.getInstance().getDaoSession().getOverTimeDao();

        mSelectDate = new ArrayList<>();
        initData();
        initCalendar();
    }

    private void initData() {
        for (OverTime overTime : getAllOver()) {
            mSelectDate.add(overTime.getDate());
        }
    }

    private void initCalendar() {
        Calendar nextMonth = Calendar.getInstance();
        nextMonth.set(Calendar.DAY_OF_MONTH, 1);
        Date minDate = nextMonth.getTime();
        nextMonth.set(Calendar.DAY_OF_MONTH, nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date maxDate = nextMonth.getTime();

        mCalendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        mCalendar.init(minDate, maxDate).withSelectedDate(today);

        mCalendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if (mSelectDate.contains(date)) {
                    Toast.makeText(MainActivity.this, "该天已加过班(*-*)", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    Log.d(TAG, "onDateSelected: 周末");
                    showTimeDialog(0, date);
                } else {
                    showAddCountDialog(date);
                    Log.d(TAG, "onDateSelected: 不是周末");
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        mCalendar.highlightDates(mSelectDate);
    }

    private void showAddCountDialog(final Date date) {
        new AlertDialog.Builder(this)
                .setTitle("加班确认")
                .setMessage("确认今天加班了吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (date.getTime() > new Date().getTime()) {
                            Toast.makeText(MainActivity.this, "请不要超越时空>*_*<", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAddCount++;
                        mSelectDate.add(date);
                        mCalendar.highlightDates(mSelectDate);
                        Log.d(TAG, "mAddCount: " + mAddCount);
                        insert(date, date, date, 1);
                    }
                })
                .show();
    }

    @SuppressWarnings("deprecation")
    private void showTimeDialog(final int type, final Date date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final TimePicker timePicker = new TimePicker(MainActivity.this);
        builder.setTitle(type == 0 ? "请选择开始时间" : "请选择结束时间");
        builder.setView(timePicker);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (type == 0) {
                    mStartHour = timePicker.getCurrentHour();
                    mStartMin = timePicker.getCurrentMinute();
                } else {
                    mEndHour = timePicker.getCurrentHour();
                    mEndMin = timePicker.getCurrentMinute();
                }
                if (type == 0) {
                    showTimeDialog(1, date);
                } else {
                    int count = (mEndHour * 60 + mEndMin - (mStartHour * 60 + mStartMin)) / 120;
                    mAddCount += count;
                    mSelectDate.add(date);
                    mCalendar.highlightDates(mSelectDate);
                    insert(date, DateUtils.getHHmm(mStartHour, mStartMin), DateUtils.getHHmm(mEndHour, mEndMin), count);
                    Log.d(TAG, "mAddCount: " + mAddCount);
                }
            }
        });
        builder.show();
    }

    private void insert(Date date, Date start, Date end, int count) {
        OverTime overTime = new OverTime();
        overTime.setCount(count);
        overTime.setCur_month(DateUtils.getDateMonth(date));
        overTime.setDate(date);
        overTime.setStartTime(start);
        overTime.setEndTime(end);
        mDao.insert(overTime);
    }

    public void search(View v) {
        Query<OverTime> query = mDao.queryBuilder()
                .where(OverTimeDao.Properties.Cur_month.eq("201608"))
                .build();

        for (OverTime overTime : query.list()) {
            Log.d(TAG, "search: " + overTime.toString());
        }

        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    private List<OverTime> getAllOver() {
        Query<OverTime> query = mDao.queryBuilder()
                .where(OverTimeDao.Properties.Cur_month.eq("201608"))
                .build();
        return query.list();
    }
}
