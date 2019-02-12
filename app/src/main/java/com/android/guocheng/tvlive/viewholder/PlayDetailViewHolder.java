package com.android.guocheng.tvlive.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.android.guocheng.tvlive.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.List;

public class PlayDetailViewHolder extends BaseViewHolder<List<String>> {

    private TextView content;

    public PlayDetailViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_play_deatil_layout);
        content = $(R.id.content);
    }

    @Override
    public void setData(final List<String> data) {
        super.setData(data);
        content.setText(data.get(0));
    }
}
