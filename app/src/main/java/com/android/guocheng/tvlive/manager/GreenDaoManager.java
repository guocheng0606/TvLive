package com.android.guocheng.tvlive.manager;


import com.android.guocheng.tvlive.app.MyApplication;
import com.android.guocheng.tvlive.model.custom.DaoMaster;
import com.android.guocheng.tvlive.model.custom.DaoSession;
import com.android.guocheng.tvlive.util.MySQLiteOpenHelper;

/**
 * Created by guocheng on 2016/12/5.
 */
public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    public GreenDaoManager() {
        /*DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), "play_db", null);
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();*/

        //MigrationHelper.DEBUG = true; //如果你想查看日志信息，请将DEBUG设置为true
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(MyApplication.getInstance(), "play_db.db",null);
        mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new GreenDaoManager();
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

}
