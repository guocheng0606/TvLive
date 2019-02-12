package com.android.guocheng.tvlive.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.didikee.donate.AlipayDonate;
import android.os.Bundle;
import android.widget.TextView;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.base.BaseFragment;
import com.android.guocheng.tvlive.util.SnackbarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 */
public class AboutMainFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @BindView(R.id.tv_info)
    TextView tv_info;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    private String versionName;

    public AboutMainFragment() {
    }

    public static AboutMainFragment newInstance(String param1, String param2) {
        AboutMainFragment fragment = new AboutMainFragment();
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
        return R.layout.fragment_about_main;
    }

    @Override
    protected void initEventAndData() {
        try {
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);
            versionName = pi.versionName;
            tv_info.setText(getString(R.string.app_name) + "   V" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.cv_about_award)
    void awardAuthor() {
        donateAlipay();
    }

    private void donateAlipay(){
        boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(getActivity());
        if (hasInstalledAlipayClient) {
            AlipayDonate.startAlipayClient(getActivity(), "FKX08859KBE78DTZQYS6EB");
        } else {
            SnackbarUtil.showShort(getActivity().getWindow().getDecorView(), "木有检测到支付宝客户端 T T");
        }
    }

}
