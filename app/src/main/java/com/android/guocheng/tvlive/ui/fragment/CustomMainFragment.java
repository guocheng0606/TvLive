package com.android.guocheng.tvlive.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.base.BaseFragment;
import com.android.guocheng.tvlive.db.CustomDbTool;
import com.android.guocheng.tvlive.event.MessageEvent;
import com.android.guocheng.tvlive.model.custom.CustomSource;
import com.android.guocheng.tvlive.model.main.Source;
import com.android.guocheng.tvlive.viewholder.CustomChannelViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 */
public class CustomMainFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    private RecyclerArrayAdapter<Source> adapter;
    private List<Source> sourceList = new ArrayList<>();

    public CustomMainFragment() {
    }

    public static CustomMainFragment newInstance(String param1, String param2) {
        CustomMainFragment fragment = new CustomMainFragment();
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
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_custom_main;
    }

    @Override
    protected void initEventAndData() {
        initView();
        getAllCustomData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()) {
            case MessageEvent.EVENT_CUSTOM_ADD:
            case MessageEvent.EVENT_CUSTOM_EDIT:
            case MessageEvent.EVENT_CUSTOM_DELETE:
                getAllCustomData();
                break;
        }
    }

    private void getAllCustomData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CustomSource> customInfoList = CustomDbTool.queryAll();
                if(customInfoList.size() > 0){
                    sourceList.clear();
                    for (CustomSource info : customInfoList) {
                        Source source = new Source();
                        source.setPosition(info.getId());
                        source.setName(info.getName());
                        Gson gson = new Gson();
                        List<List<String>> url = gson.fromJson(info.getUrl(),new TypeToken<List<List<String>>>(){}.getType());
                        source.setUrl(url);
                        sourceList.add(source);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.clear();
                            adapter.addAll(sourceList);
                        }
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.clear();
                        }
                    });
                }
            }
        }).start();
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
                return new CustomChannelViewHolder(parent);
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
        recyclerView.setRefreshListener(this);
    }

    @Override
    public void onLoadMore() {
        adapter.stopMore();
    }

    @Override
    public void onRefresh() {
        getAllCustomData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
