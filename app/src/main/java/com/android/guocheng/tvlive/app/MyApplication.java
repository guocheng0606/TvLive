package com.android.guocheng.tvlive.app;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.android.guocheng.tvlive.crash.CrashHandler;
import com.android.guocheng.tvlive.manager.GreenDaoManager;

import java.util.HashSet;
import java.util.Set;

import cn.bmob.v3.Bmob;


public class MyApplication extends MultiDexApplication {

    public static MyApplication instance;
    private Set<Activity> allActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        GreenDaoManager.getInstance();
        Bmob.initialize(this, "5c98c79b797bcdc22ef055e3a0602556");
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }


    public static synchronized MyApplication getInstance() {
        return instance;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
