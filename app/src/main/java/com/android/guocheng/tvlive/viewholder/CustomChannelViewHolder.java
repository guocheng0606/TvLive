package com.android.guocheng.tvlive.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.db.CustomDbTool;
import com.android.guocheng.tvlive.event.MessageEvent;
import com.android.guocheng.tvlive.model.main.Source;
import com.android.guocheng.tvlive.ui.activity.CustomAddActivity;
import com.android.guocheng.tvlive.ui.activity.PlayVideoActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.greenrobot.eventbus.EventBus;

public class CustomChannelViewHolder extends BaseViewHolder<Source> {

    private SwipeMenuLayout swipeMenuLayout;
    private TextView content;
    private Button btn_edit;
    private Button btn_delete;

    public CustomChannelViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_custom_layout);
        swipeMenuLayout = $(R.id.swipeMenuLayout);
        content = $(R.id.content);
        btn_edit = $(R.id.btn_edit);
        btn_delete = $(R.id.btn_delete);
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
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CustomAddActivity.class);
                intent.putExtra("info", data);
                getContext().startActivity(intent);
                swipeMenuLayout.smoothClose();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确认删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CustomDbTool.deleteById(data.getPosition());
                                EventBus.getDefault().post(new MessageEvent(MessageEvent.EVENT_CUSTOM_DELETE));
                            }
                        }).show();

            }
        });
    }
}
