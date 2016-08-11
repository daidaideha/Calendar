package com.lyl.calendar.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyl.calendar.History;
import com.lyl.calendar.R;
import com.lyl.calendar.SampleDecorator;
import com.lyl.calendar.dao.HistoryDate;
import com.lyl.calendar.handlers.HistoryHandlers;
import com.lyl.calendar.utils.DateUtils;
import com.lyl.calendar.widget.calendar.CalendarCellDecorator;
import com.lyl.calendar.widget.calendar.CalendarPickerView;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by lyl on 2016/8/4.
 */

public class HistoryFragment extends Fragment {

    public static final String TAG = HistoryFragment.class.getSimpleName();

    private History mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: " + hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        mBinding = DataBindingUtil.bind(view);
        mBinding.setHandler(new HistoryHandlers());
        mBinding.setBean(new HistoryDate(DateUtils.getDateMonth(new Date()), DateUtils.getDateMonth(new Date())));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar nextMonth = Calendar.getInstance();
        nextMonth.set(Calendar.DAY_OF_MONTH, 1);
        Date minDate = nextMonth.getTime();
        nextMonth.add(Calendar.MONTH, 3);
        nextMonth.set(Calendar.DAY_OF_MONTH, nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        nextMonth.add(Calendar.DAY_OF_MONTH, 1);
        Date maxDate = nextMonth.getTime();

        Date today = new Date();
        mBinding.calendar.init(minDate, maxDate).withSelectedDate(today);
        mBinding.calendar.setDecorators(Collections.<CalendarCellDecorator>singletonList(new SampleDecorator()));

        mBinding.calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
}
