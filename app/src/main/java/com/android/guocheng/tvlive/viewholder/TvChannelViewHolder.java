package com.android.guocheng.tvlive.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.model.main.Source;
import com.android.guocheng.tvlive.ui.activity.PlayVideoActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class TvChannelViewHolder extends BaseViewHolder<Source> {

    private TextView content;

    public TvChannelViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_grid_layout);
        content = $(R.id.content);
    }

    @Override
    public void setData(final Source data) {
        super.setData(data);
        content.setText(data.getName());
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PlayVideoActivity.class);
                intent.putExtra("tv_source", data);
                getContext().startActivity(intent);
            }
        });
    }

}
