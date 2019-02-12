package com.android.guocheng.tvlive.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.didikee.donate.AlipayDonate;
import android.didikee.donate.WeiXinDonate;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.app.Constants;
import com.android.guocheng.tvlive.app.MyApplication;
import com.android.guocheng.tvlive.base.BaseActivity;
import com.android.guocheng.tvlive.ui.fragment.AboutMainFragment;
import com.android.guocheng.tvlive.ui.fragment.CustomMainFragment;
import com.android.guocheng.tvlive.ui.fragment.HomeMainFragment;
import com.android.guocheng.tvlive.ui.fragment.LiveMainFragment;
import com.android.guocheng.tvlive.ui.fragment.SettingsMainFragment;
import com.android.guocheng.tvlive.util.SnackbarUtil;
import com.jaeger.library.StatusBarUtil;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.io.File;
import java.io.InputStream;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_CODE = 2233;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private HomeMainFragment homeMainFragment;
    private LiveMainFragment liveMainFragment;
    private SettingsMainFragment settingsMainFragment;
    private CustomMainFragment customMainFragment;
    private AboutMainFragment aboutMainFragment;

    private MenuItem mLastMenuItem;
    private MenuItem mSearchMenuItem;
    private MenuItem mAddMenuItem;

    private int hideFragment = Constants.TYPE_HOME;
    private int showFragment = Constants.TYPE_HOME;

    private SearchFragment searchFragment = SearchFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, mDrawerLayout, 0xFF008577);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mToolbar,"首页");
        homeMainFragment = new HomeMainFragment();
        liveMainFragment = new LiveMainFragment();
        settingsMainFragment = new SettingsMainFragment();
        customMainFragment = new CustomMainFragment();
        aboutMainFragment = new AboutMainFragment();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mLastMenuItem = mNavigationView.getMenu().findItem(R.id.nav_home);
        loadMultipleRootFragment(R.id.fl_main_content,0,homeMainFragment,liveMainFragment,
                settingsMainFragment,customMainFragment,aboutMainFragment);

        mNavigationView.setNavigationItemSelectedListener(this);

        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                Intent intent = new Intent(MainActivity.this,SearchResultActivity.class);
                intent.putExtra("search",keyword);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出吗？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyApplication.getInstance().exitApp();
            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(true);
        menu.findItem(R.id.action_add).setVisible(false);
        mSearchMenuItem = item;
        mAddMenuItem = menu.findItem(R.id.action_add);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                searchFragment.show(getSupportFragmentManager(),SearchFragment.TAG);
                break;
            case R.id.action_add:
                startActivity(new Intent(MainActivity.this, CustomAddActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_donate) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("请选择")
                    .setItems(new String[]{"支付宝", "微信"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    //支付宝捐赠
                                    donateAlipay();
                                    break;
                                case 1:
                                    //微信捐赠
                                    checkPermissionAndDonateWeixin();
                                    break;
                            }
                        }
                    }).show();
            return true;
        }
        if (id == R.id.nav_home) {
            showFragment = Constants.TYPE_HOME;
            mSearchMenuItem.setVisible(true);
            mAddMenuItem.setVisible(false);
        } else if (id == R.id.nav_live) {
            showFragment = Constants.TYPE_LIVE;
            mSearchMenuItem.setVisible(false);
            mAddMenuItem.setVisible(false);
        } else if (id == R.id.nav_settings) {
            showFragment = Constants.TYPE_SETTINGS;
            mSearchMenuItem.setVisible(false);
            mAddMenuItem.setVisible(false);
        }else if (id == R.id.nav_custom) {
            showFragment = Constants.TYPE_CUSTOM;
            mSearchMenuItem.setVisible(false);
            mAddMenuItem.setVisible(true);
        } else if (id == R.id.nav_adout) {
            showFragment = Constants.TYPE_ABOUT;
            mSearchMenuItem.setVisible(false);
            mAddMenuItem.setVisible(false);
        }
        if(mLastMenuItem != null) {
            mLastMenuItem.setChecked(false);
        }
        mLastMenuItem = item;
        item.setChecked(true);
        mToolbar.setTitle(item.getTitle());
        mDrawerLayout.closeDrawers();
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        hideFragment = showFragment;
        return true;
    }

    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.TYPE_HOME:
                return homeMainFragment;
            case Constants.TYPE_LIVE:
                return liveMainFragment;
            case Constants.TYPE_SETTINGS:
                return settingsMainFragment;
            case Constants.TYPE_CUSTOM:
                return customMainFragment;
            case Constants.TYPE_ABOUT:
                return aboutMainFragment;
        }
        return homeMainFragment;
    }

    private int getCurrentItem(int item) {
        switch (item) {
            case Constants.TYPE_HOME:
                return R.id.nav_home;
            case Constants.TYPE_LIVE:
                return R.id.nav_live;
            case Constants.TYPE_SETTINGS:
                return R.id.nav_settings;
            case Constants.TYPE_CUSTOM:
                return R.id.nav_custom;
            case Constants.TYPE_ABOUT:
                return R.id.nav_adout;
        }
        return R.id.nav_home;
    }

    private void donateAlipay(){
        boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(this);
        if (hasInstalledAlipayClient) {
            AlipayDonate.startAlipayClient(this, "FKX08859KBE78DTZQYS6EB");
        } else {
            SnackbarUtil.showShort(this.getWindow().getDecorView(), "木有检测到支付宝客户端 T T");
        }
    }

    private void checkPermissionAndDonateWeixin() {
        //检测微信是否安装
        if (!WeiXinDonate.hasInstalledWeiXinClient(this)) {
            SnackbarUtil.showShort(this.getWindow().getDecorView(), "木有检测到微信客户端 T T");
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //已经有权限
            showDonateTipDialog();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    private void showDonateTipDialog() {
        new AlertDialog.Builder(this)
                .setTitle("微信捐赠操作步骤")
                .setMessage("点击确定按钮后会跳转微信扫描二维码界面：\n\n" + "1. 点击右上角的菜单按钮\n\n" + "2. 点击'从相册选取二维码'\n\n" + "3. 选择第一张二维码图片即可\n\n")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        donateWeixin();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 需要提前准备好 微信收款码 照片，可通过微信客户端生成
     */
    private void donateWeixin() {
        InputStream weixinQrIs = getResources().openRawResource(R.raw.didikee_weixin);
        String qrPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AndroidDonate" + File.separator +
                "didikee_weixin.png";
        WeiXinDonate.saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs));
        WeiXinDonate.donateViaWeiXin(this, qrPath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            donateWeixin();
        } else {
            SnackbarUtil.show(this.getWindow().getDecorView(), "权限被拒绝 T T");
        }
    }
}
