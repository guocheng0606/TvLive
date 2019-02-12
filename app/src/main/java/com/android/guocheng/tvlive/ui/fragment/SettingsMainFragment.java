package com.android.guocheng.tvlive.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.base.BaseFragment;
import com.android.guocheng.tvlive.manager.DataCleanManager;
import com.android.guocheng.tvlive.util.ProgressDialogUtils;
import com.android.guocheng.tvlive.util.ShareUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import butterknife.BindView;

/**
 */
public class SettingsMainFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.ll_setting_feedback)
    LinearLayout ll_setting_feedback;
    @BindView(R.id.ll_setting_clear)
    LinearLayout ll_setting_clear;
    @BindView(R.id.tv_setting_clear)
    TextView tv_setting_clear;
    @BindView(R.id.ll_setting_update)
    LinearLayout ll_setting_update;
    @BindView(R.id.tv_setting_update)
    TextView tv_setting_update;

    private String versionName;

    public SettingsMainFragment() {
    }

    public static SettingsMainFragment newInstance(String param1, String param2) {
        SettingsMainFragment fragment = new SettingsMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings_main;
    }

    @Override
    protected void initEventAndData() {
        try {
            tv_setting_clear.setText(DataCleanManager.getTotalCacheSize(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);
            versionName = pi.versionName;
            tv_setting_update.setText(String.format("当前版本号 v%s",versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ll_setting_feedback.setOnClickListener(this);
        ll_setting_clear.setOnClickListener(this);
        ll_setting_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_setting_feedback:
                ShareUtil.sendEmail(mContext, "选择邮件客户端:");
                break;
            case R.id.ll_setting_clear:
                GSYVideoManager.instance().clearAllDefaultCache(getActivity());
                ProgressDialogUtils.showLoading(getActivity());
                boolean b = DataCleanManager.clearAllCache(getActivity());
                if (b) {
                    ProgressDialogUtils.dismissLoading();
                    try {
                        tv_setting_clear.setText(DataCleanManager.getTotalCacheSize(getActivity()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.ll_setting_update:
                Snackbar.make(ll_setting_update, "已经是最新版本~", Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}
