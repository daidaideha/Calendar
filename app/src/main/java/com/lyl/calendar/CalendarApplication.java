package com.lyl.calendar;

import android.app.Application;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import com.lyl.calendar.dao.DaoMaster;
import com.lyl.calendar.dao.DaoSession;

/**
 * Created by lyl on 2016/8/3.
 */

public class CalendarApplication extends Application {

    private static CalendarApplication mInstance = null;
    private SQLiteDatabase mDb;
    private DaoSession mDaoSession;
    private int mCurPayPrice;

    public static CalendarApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setupDatabase();
    }

    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "calendar-db", null);
        mDb = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoSession = new DaoMaster(mDb).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

    public int getCurPayPrice() {
        return mCurPayPrice;
    }

    public void setCurPayPrice(int curPayPrice) {
        this.mCurPayPrice = curPayPrice;
    }

    public int getVersionCode() {
        int version = -1;
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
