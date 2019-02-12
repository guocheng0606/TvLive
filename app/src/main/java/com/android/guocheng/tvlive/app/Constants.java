package com.android.guocheng.tvlive.app;

import android.os.Environment;

import java.io.File;

/**
 */
public class Constants {

    //================= TYPE ====================

    public static final int TYPE_HOME = 101;

    public static final int TYPE_LIVE = 102;

    public static final int TYPE_SETTINGS = 103;

    public static final int TYPE_CUSTOM = 104;

    public static final int TYPE_ABOUT = 105;

    public static final int NEWS_CHANNEL_ENABLE = 1;
    public static final int NEWS_CHANNEL_DISABLE = 0;


    //================= PATH ====================

    public static final String PATH_DATA = MyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";

}
