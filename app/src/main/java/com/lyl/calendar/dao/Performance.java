package com.lyl.calendar.dao;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lyl.calendar.BR;

/**
 * Created by lyl on 2016/8/4.
 */
public class Performance extends BaseObservable {

    private int mAddCount;
    private int mOverTimePerformance;
    private int mJobPerformance;
    private float mPayPrice;

    @Bindable
    public int getAddCount() {
        return mAddCount;
    }

    @Bindable
    public int getOverTimePerformance() {
        return mOverTimePerformance;
    }

    @Bindable
    public int getJobPerformance() {
        return mJobPerformance;
    }

    @Bindable
    public float getPayPrice() {
        return mPayPrice;
    }

    public void setAddCount(int addCount) {
        this.mAddCount = addCount;
        int performance = 100;
        if (mAddCount >= 1 && mAddCount <=5) {
            performance = 110;
        } else if (mAddCount > 5 && mAddCount <= 10) {
            performance = 125;
        } else if (mAddCount > 10 && mAddCount <= 15) {
            performance = 150;
        } else if (mAddCount > 15 && mAddCount <= 20) {
            performance = 155;
        } else if (mAddCount > 20) {
            performance = 160;
        }
        setOverTimePerformance(performance);
        //tools
        notifyPropertyChanged(BR.addCount);
    }

    public void setOverTimePerformance(int overTimePerformance) {
        this.mOverTimePerformance = overTimePerformance;
        notifyPropertyChanged(BR.overTimePerformance);
    }

    public void setJobPerformance(int jobPerformance) {
        this.mJobPerformance = jobPerformance;
        notifyPropertyChanged(BR.jobPerformance);
    }

    public void setPayPrice(float payPrice) {
        this.mPayPrice = payPrice;
        notifyPropertyChanged(BR.addCount);
    }
}
