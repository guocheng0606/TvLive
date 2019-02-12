package com.android.guocheng.tvlive.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.base.BaseFragment;
import com.android.guocheng.tvlive.model.main.AnimSource;
import com.android.guocheng.tvlive.model.main.MovieSource;
import com.android.guocheng.tvlive.model.main.Source;
import com.android.guocheng.tvlive.model.main.TvSource;
import com.android.guocheng.tvlive.model.main.VarietySource;
import com.android.guocheng.tvlive.viewholder.TvChannelViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 */
public class ChannelChildFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Source> adapter;

    private List<Source> sourceList = new ArrayList<>();

    private int currentPage = 0;

    public ChannelChildFragment() {
    }

    public static ChannelChildFragment newInstance(String param1, String param2) {
        ChannelChildFragment fragment = new ChannelChildFragment();
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
        return R.layout.fragment_channel_child;
    }

    @Override
    protected void initEventAndData() {
        initView();
        getData();
    }

    private void getData() {
        switch (mParam1) {
            case "电视剧":
                new BmobQuery<TvSource>().setLimit(100).setSkip(currentPage * 100).order("position").findObjects(new FindListener<TvSource>() {
                    @Override
                    public void done(List<TvSource> list, BmobException e) {
                        if (e == null) {
                            Log.d("bmob", "成功：共" + list.size() + "条数据");

                            if(list.size() > 0) {
                                sourceList.clear();
                                for (TvSource bean : list) {
                                    Source source = new Source();
                                    source.setName(bean.getName());
                                    source.setRemark(bean.getRemark());
                                    source.setPosition(Long.valueOf(bean.getPosition()));
                                    source.setUrl(bean.getUrl());
                                    sourceList.add(source);
                                }
                                if(currentPage == 0)
                                    adapter.clear();
                                adapter.addAll(sourceList);
                            } else {
                                if (currentPage == 0) {
                                    adapter.clear();
                                } else {
                                    adapter.stopMore();
                                }
                            }
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
            case "电影":
                new BmobQuery<MovieSource>().setLimit(100).setSkip(currentPage * 100).order("position").findObjects(new FindListener<MovieSource>() {
                    @Override
                    public void done(List<MovieSource> list, BmobException e) {
                        if (e == null) {
                            Log.d("bmob", "成功：共" + list.size() + "条数据");
                            if(list.size() > 0) {
                                sourceList.clear();
                                for (MovieSource bean : list) {
                                    Source source = new Source();
                                    source.setName(bean.getName());
                                    source.setRemark(bean.getRemark());
                                    source.setPosition(Long.valueOf(bean.getPosition()));
                                    source.setUrl(bean.getUrl());
                                    sourceList.add(source);
                                }
                                if(currentPage == 0)
                                    adapter.clear();
                                adapter.addAll(sourceList);
                            } else {
                                if (currentPage == 0) {
                                    adapter.clear();
                                } else {
                                    adapter.stopMore();
                                }
                            }
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
            case "动漫":
                new BmobQuery<AnimSource>().setLimit(100).setSkip(currentPage * 100).order("position").findObjects(new FindListener<AnimSource>() {
                    @Override
                    public void done(List<AnimSource> list, BmobException e) {
                        if (e == null) {
                            Log.d("bmob", "成功：共" + list.size() + "条数据");
                            if(list.size() > 0) {
                                sourceList.clear();
                                for (AnimSource bean : list) {
                                    Source source = new Source();
                                    source.setName(bean.getName());
                                    source.setRemark(bean.getRemark());
                                    source.setPosition(Long.valueOf(bean.getPosition()));
                                    source.setUrl(bean.getUrl());
                                    sourceList.add(source);
                                }
                                if(currentPage == 0)
                                    adapter.clear();
                                adapter.addAll(sourceList);
                            } else {
                                if (currentPage == 0) {
                                    adapter.clear();
                                } else {
                                    adapter.stopMore();
                                }
                            }
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
            case "综艺":
                new BmobQuery<VarietySource>().setLimit(100).setSkip(currentPage * 100).order("position").findObjects(new FindListener<VarietySource>() {
                    @Override
                    public void done(List<VarietySource> list, BmobException e) {
                        if (e == null) {
                            Log.d("bmob", "成功：共" + list.size() + "条数据");
                            if(list.size() > 0) {
                                sourceList.clear();
                                for (VarietySource bean : list) {
                                    Source source = new Source();
                                    source.setName(bean.getName());
                                    source.setRemark(bean.getRemark());
                                    source.setPosition(Long.valueOf(bean.getPosition()));
                                    source.setUrl(bean.getUrl());
                                    sourceList.add(source);
                                }
                                if(currentPage == 0)
                                    adapter.clear();
                                adapter.addAll(sourceList);
                            } else {
                                if (currentPage == 0) {
                                    adapter.clear();
                                } else {
                                    adapter.stopMore();
                                }
                            }
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
        }
    }

    private void initView() {
        recyclerView.setEmptyView(R.layout.view_empty);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(0xFFEDEDED, 1, 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Source>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new TvChannelViewHolder(parent);
            }
        });
        gridLayoutManager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(2));
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
        currentPage++;
        getData();
    }
}
