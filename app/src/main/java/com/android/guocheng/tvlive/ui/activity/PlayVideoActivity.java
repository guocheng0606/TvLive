package com.android.guocheng.tvlive.ui.activity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.app.MyApplication;
import com.android.guocheng.tvlive.model.main.Source;
import com.android.guocheng.tvlive.viewholder.PlayDetailViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.NetworkUtils;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlayVideoActivity extends AppCompatActivity {

    @BindView(R.id.detail_player)
    StandardGSYVideoPlayer detailPlayer;
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private OrientationUtils orientationUtils;

    private Source tvSource;
    private boolean isPlay;
    private boolean isPause;
    private Unbinder mUnBinder;
    private RecyclerArrayAdapter<List<String>> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mUnBinder = ButterKnife.bind(this);
        StatusBarUtil.setColor(this,0xFF000000);
        MyApplication.getInstance().addActivity(this);

        tvSource = (Source) getIntent().getSerializableExtra("tv_source");

        initView();
        initEventAndData();
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PlayVideoActivity.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);

        DividerDecoration itemDecoration = new DividerDecoration(0xFFEDEDED, 1, 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<List<String>>(PlayVideoActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new PlayDetailViewHolder(parent);
            }
        });
        gridLayoutManager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(2));
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startPlayer(adapter.getAllData().get(position).get(1), tvSource.getName()+" - "+adapter.getAllData().get(position).get(0),true);
            }
        });
        adapter.addAll(tvSource.getUrl());
    }

    protected void initEventAndData() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        startPlayer(tvSource.getUrl().get(0).get(1), tvSource.getName()+" - "+adapter.getAllData().get(0).get(0),false);
        detailPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void startPlayer(final String url, final String title, final boolean isAutoPlay) {
        if (NetworkUtils.isWifiConnected(PlayVideoActivity.this)) {
            setvVideoOption(url, title,isAutoPlay);
        } else {
            new AlertDialog.Builder(PlayVideoActivity.this)
                    .setTitle("提示")
                    .setMessage("您当前处于非WiFi网络，继续播放将消耗流量")
                    .setNegativeButton("取消播放", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("继续播放", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            setvVideoOption(url, title,isAutoPlay);
                        }
                    })
                    .create().show();
        }
    }

    private void setvVideoOption(String url, String title,boolean isAutoPlay) {
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption//.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle(title)
                .setNeedShowWifiTip(true)
                .setSeekRatio(1)
                .setBottomProgressBarDrawable(new ColorDrawable(0x00000000))
                .setVideoAllCallBack(new GSYSampleCallBack(){
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                }).build(detailPlayer);
        if (isAutoPlay)
            detailPlayer.startPlayLogic();
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(PlayVideoActivity.this, true, true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        detailPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        detailPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        MyApplication.getInstance().removeActivity(this);
        mUnBinder.unbind();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay &&! isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

}
