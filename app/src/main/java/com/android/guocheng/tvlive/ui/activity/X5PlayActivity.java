package com.android.guocheng.tvlive.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.model.main.Source;

import butterknife.ButterKnife;

public class X5PlayActivity extends AppCompatActivity {

    private Source tvSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5_play);
        ButterKnife.bind(this);

        tvSource = (Source) getIntent().getSerializableExtra("tv_source");

    }


}
