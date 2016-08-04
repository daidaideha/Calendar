package com.lyl.calendar.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.lyl.calendar.dao.OverTime;

import com.lyl.calendar.dao.OverTimeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig overTimeDaoConfig;

    private final OverTimeDao overTimeDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        overTimeDaoConfig = daoConfigMap.get(OverTimeDao.class).clone();
        overTimeDaoConfig.initIdentityScope(type);

        overTimeDao = new OverTimeDao(overTimeDaoConfig, this);

        registerDao(OverTime.class, overTimeDao);
    }
    
    public void clear() {
        overTimeDaoConfig.getIdentityScope().clear();
    }

    public OverTimeDao getOverTimeDao() {
        return overTimeDao;
    }

}