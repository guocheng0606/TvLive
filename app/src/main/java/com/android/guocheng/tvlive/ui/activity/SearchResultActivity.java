package com.android.guocheng.tvlive.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.base.BaseActivity;
import com.android.guocheng.tvlive.model.main.AnimSource;
import com.android.guocheng.tvlive.model.main.MovieSource;
import com.android.guocheng.tvlive.model.main.Source;
import com.android.guocheng.tvlive.model.main.TvSource;
import com.android.guocheng.tvlive.model.main.VarietySource;
import com.android.guocheng.tvlive.viewholder.TvChannelViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SearchResultActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private String keyword;
    private List<Source> sourceList = new ArrayList<>();
    private RecyclerArrayAdapter<Source> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolbar,"查找");
        keyword = getIntent().getStringExtra("search");
        initView();
        queryTvDatas();
    }

    private void queryTvDatas() {
        final int[] currentPage = {0};
        BmobQuery<TvSource> query = new BmobQuery<>();
        //query.addWhereEqualTo("name", keyword);
        query.setLimit(500);
        query.setSkip(currentPage[0] * 500);
        query.findObjects(new FindListener<TvSource>() {
            @Override
            public void done(List<TvSource> list, BmobException e) {
                if(e == null){
                    Log.d("bmob", "成功：共" + list.size() + "条数据");
                    if(list.size() > 0) {
                        for (TvSource bean : list) {
                            if (bean.getName().contains(keyword)) {
                                Source source = new Source();
                                source.setName(bean.getName());
                                source.setRemark(bean.getRemark());
                                source.setPosition(Long.valueOf(bean.getPosition()));
                                source.setUrl(bean.getUrl());
                                sourceList.add(source);
                            }

                        }
                        if (list.size() < 500){
                            queryMovieDatas();
                        } else {
                            currentPage[0]++;
                            queryTvDatas();
                        }

                    } else {
                        queryMovieDatas();
                    }
                }else{
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
    private void queryMovieDatas() {
        final int[] currentPage = {0};
        BmobQuery<MovieSource> query = new BmobQuery<>();
        query.setLimit(500);
        query.setSkip(currentPage[0] * 500);
        query.findObjects(new FindListener<MovieSource>() {
            @Override
            public void done(List<MovieSource> list, BmobException e) {
                if(e == null){
                    Log.d("bmob", "成功：共" + list.size() + "条数据");
                    if(list.size() > 0) {
                        for (MovieSource bean : list) {
                            if (bean.getName().contains(keyword)) {
                                Source source = new Source();
                                source.setName(bean.getName());
                                source.setRemark(bean.getRemark());
                                source.setPosition(Long.valueOf(bean.getPosition()));
                                source.setUrl(bean.getUrl());
                                sourceList.add(source);
                            }

                        }
                        if (list.size() < 500){
                            queryAnimDatas();
                        } else {
                            currentPage[0]++;
                            queryTvDatas();
                        }

                    } else {
                        queryAnimDatas();
                    }
                }else{
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
    private void queryAnimDatas() {
        final int[] currentPage = {0};
        BmobQuery<AnimSource> query = new BmobQuery<>();
        query.setLimit(500);
        query.setSkip(currentPage[0] * 500);
        query.findObjects(new FindListener<AnimSource>() {
            @Override
            public void done(List<AnimSource> list, BmobException e) {
                if(e == null){
                    Log.d("bmob", "成功：共" + list.size() + "条数据");
                    if(list.size() > 0) {
                        for (AnimSource bean : list) {
                            if (bean.getName().contains(keyword)) {
                                Source source = new Source();
                                source.setName(bean.getName());
                                source.setRemark(bean.getRemark());
                                source.setPosition(Long.valueOf(bean.getPosition()));
                                source.setUrl(bean.getUrl());
                                sourceList.add(source);
                            }

                        }
                        if (list.size() < 500){
                            queryVarietyDatas();
                        } else {
                            currentPage[0]++;
                            queryTvDatas();
                        }

                    } else {
                        queryVarietyDatas();
                    }
                }else{
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
    private void queryVarietyDatas() {
        final int[] currentPage = {0};
        BmobQuery<VarietySource> query = new BmobQuery<>();
        query.setLimit(500);
        query.setSkip(currentPage[0] * 500);
        query.findObjects(new FindListener<VarietySource>() {
            @Override
            public void done(List<VarietySource> list, BmobException e) {
                if(e == null){
                    Log.d("bmob", "成功：共" + list.size() + "条数据");
                    if(list.size() > 0) {
                        for (VarietySource bean : list) {
                            if (bean.getName().contains(keyword)) {
                                Source source = new Source();
                                source.setName(bean.getName());
                                source.setRemark(bean.getRemark());
                                source.setPosition(Long.valueOf(bean.getPosition()));
                                source.setUrl(bean.getUrl());
                                sourceList.add(source);
                            }

                        }
                        if (list.size() < 500){
                            if (sourceList.size() > 0) {
                                adapter.addAll(sourceList);
                            } else {
                                adapter.clear();
                            }
                        } else {
                            currentPage[0]++;
                            queryTvDatas();
                        }

                    } else {
                        if (sourceList.size() > 0) {
                            adapter.addAll(sourceList);
                        } else {
                            adapter.clear();
                        }
                    }
                }else{
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void initView() {
        recyclerView.setEmptyView(R.layout.view_empty);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        /*DividerDecoration itemDecoration = new DividerDecoration(0xFFEDEDED, 1, 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);*/
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Source>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new TvChannelViewHolder(parent);
            }
        });
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.resumeMore();
            }

            @Override
            public void onNoMoreClick() {
                adapter.resumeMore();
            }
        });
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
    }

    @Override
    public void onLoadMore() {
        adapter.stopMore();
    }
}
