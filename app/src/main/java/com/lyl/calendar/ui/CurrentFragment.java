package com.lyl.calendar.ui;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lyl.calendar.CalendarApplication;
import com.lyl.calendar.Current;
import com.lyl.calendar.R;
import com.lyl.calendar.SampleDecorator;
import com.lyl.calendar.dao.OverTime;
import com.lyl.calendar.dao.OverTimeDao;
import com.lyl.calendar.dao.Performance;
import com.lyl.calendar.utils.DateUtils;
import com.lyl.calendar.widget.calendar.CalendarCellDecorator;
import com.lyl.calendar.widget.calendar.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by lyl on 2016/8/3.
 * </p>
 * 当前月
 */
public class CurrentFragment extends Fragment {

    public static final String TAG = CurrentFragment.class.getSimpleName();

    private int mStartHour, mStartMin, mEndHour, mEndMin;
    private Collection<Date> mSelectDate;
    private OverTimeDao mDao;
    private Current mBinding;
    private Performance mCurrent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectDate = new ArrayList<>();
        mCurrent = new Performance();
        int addCount = 0;
        mDao = CalendarApplication.getInstance().getDaoSession().getOverTimeDao();
        for (OverTime overTime : getAllOver()) {
            mSelectDate.add(overTime.getDate());
            addCount += overTime.getCount();
        }
        mCurrent.setAddCount(addCount);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_fragment, container, false);
        mBinding = DataBindingUtil.bind(view);
        mBinding.setPerformance(mCurrent);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar nextMonth = Calendar.getInstance();
        nextMonth.set(Calendar.DAY_OF_MONTH, 1);
        Date minDate = nextMonth.getTime();
        nextMonth.set(Calendar.DAY_OF_MONTH, nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        nextMonth.add(Calendar.DAY_OF_MONTH, 1);
        Date maxDate = nextMonth.getTime();

        Date today = new Date();
        mBinding.calendarView.init(minDate, maxDate).withSelectedDate(today);
        mBinding.calendarView.setDecorators(Collections.<CalendarCellDecorator>singletonList(new SampleDecorator()));

        mBinding.calendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if (date.getTime() > new Date().getTime()) {
                    return;
                }
                if (mSelectDate.contains(date)) {
                    showAddCountDialog(date, 1);
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                            || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        showTimeDialog(date, 0);
                    } else {
                        showAddCountDialog(date, 0);
                    }
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        mBinding.calendarView.highlightDates(mSelectDate);
    }

    private void showAddCountDialog(final Date date, final int type) {
        new AlertDialog.Builder(getActivity())
                .setTitle(type == 0 ? "加班确认" : "删除确认")
                .setMessage(type == 0 ? "确认今天加班了吗？" : "确认删除该天的加班记录？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (date.getTime() > new Date().getTime()) {
                            Toast.makeText(getActivity(), "请不要超越时空>*_*<", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (type == 0) {
                            mCurrent.setAddCount(mCurrent.getAddCount() + 1);
                            mSelectDate.add(date);
                            insert(date, date, date, 1);
                        } else {
                            mCurrent.setAddCount(mCurrent.getAddCount() - 1);
                            mSelectDate.remove(date);
                            delete(date);
                        }
                        mBinding.calendarView.clearHighlightedDates();
                        mBinding.calendarView.highlightDates(mSelectDate);
                    }
                })
                .show();
    }

    @SuppressWarnings("deprecation")
    private void showTimeDialog(final Date date, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final TimePicker timePicker = new TimePicker(getActivity());
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
                    showTimeDialog(date, 1);
                } else {
                    if (date.getTime() > new Date().getTime()) {
                        Toast.makeText(getActivity(), "请不要超越时空>*_*<", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int count = (mEndHour * 60 + mEndMin - (mStartHour * 60 + mStartMin)) / 120;
                    mCurrent.setAddCount(mCurrent.getAddCount() + count);
                    mSelectDate.add(date);
                    mBinding.calendarView.highlightDates(mSelectDate);
                    insert(date, DateUtils.getHHmm(mStartHour, mStartMin), DateUtils.getHHmm(mEndHour, mEndMin), count);
                }
            }
        });
        builder.show();
    }

    private void insert(Date date, Date start, Date end, int count) {
        OverTime overTime = new OverTime();
        overTime.setCount(count);
        overTime.setCurMonth(DateUtils.getDateMonth(date));
        overTime.setDate(date);
        overTime.setStartTime(start);
        overTime.setEndTime(end);
        mDao.insert(overTime);
    }

    private void delete(Date date) {
        for (OverTime overTime : getAllOver()) {
            if (overTime.getDate().equals(date)) {
                mDao.delete(overTime);
                break;
            }
        }
    }

    public void search(String search) {
        Query<OverTime> query = mDao.queryBuilder()
                .where(OverTimeDao.Properties.CurMonth.eq(search))
                .build();

        for (OverTime overTime : query.list()) {
            Log.d(TAG, "search: " + overTime.toString());
        }

        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    private List<OverTime> getAllOver() {
        Query<OverTime> query = mDao.queryBuilder()
                .where(OverTimeDao.Properties.CurMonth.eq(DateUtils.getDateMonth(new Date())))
                .build();
        return query.list();
    }
}
