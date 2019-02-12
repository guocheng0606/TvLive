package com.android.guocheng.tvlive.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.adapter.MyViewPagerAdapter;
import com.android.guocheng.tvlive.base.BaseFragment;

import butterknife.BindView;

/**
 */
public class HomeMainFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPager;

    public HomeMainFragment() {
    }

    public static HomeMainFragment newInstance(String param1, String param2) {
        HomeMainFragment fragment = new HomeMainFragment();
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
        return R.layout.fragment_home_main;
    }

    @Override
    protected void initEventAndData() {
        setupTabLayout();
    }

    private void setupTabLayout() {
        //ViewPager关联适配器
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(ChannelChildFragment.newInstance("电视剧", null), "电视剧");
        adapter.addFragment(ChannelChildFragment.newInstance("电影", null), "电影");
        adapter.addFragment(ChannelChildFragment.newInstance("动漫", null), "动漫");
        adapter.addFragment(ChannelChildFragment.newInstance("综艺", null), "综艺");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        //ViewPager和TabLayout关联
        tabLayout.setupWithViewPager(viewPager);
    }

}
