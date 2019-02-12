package com.android.guocheng.tvlive.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.android.guocheng.tvlive.R;
import com.android.guocheng.tvlive.base.BaseActivity;
import com.android.guocheng.tvlive.db.CustomDbTool;
import com.android.guocheng.tvlive.event.MessageEvent;
import com.android.guocheng.tvlive.model.custom.CustomSource;
import com.android.guocheng.tvlive.model.main.Source;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class CustomAddActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.met_name)
    MaterialEditText metName;
    @BindView(R.id.met_url)
    MaterialEditText metUrl;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private Source source = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_custom_add;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolbar,"");
        source = (Source) getIntent().getSerializableExtra("info");
        if(null != source){
            getSupportActionBar().setTitle(R.string.edit);
            metName.setText(source.getName());
            metUrl.setText(source.getUrl().get(0).get(1));
        }else{
            getSupportActionBar().setTitle(R.string.add);
        }
        initListener();
    }

    private void initListener() {
        btnOk.setOnClickListener(this);
        metName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if(TextUtils.isEmpty(metName.getText().toString()))
                        metName.setError("不能为空");
                }
            }
        });
        metUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if(TextUtils.isEmpty(metUrl.getText().toString()))
                        metUrl.setError("不能为空");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                if(TextUtils.isEmpty(metName.getText().toString())){
                    metName.setError("不能为空");
                    return;
                }
                if(TextUtils.isEmpty(metUrl.getText().toString())){
                    metUrl.setError("不能为空");
                    return;
                }
                if(null != source){
                    CustomSource custom = new CustomSource();
                    custom.setId(source.getPosition());
                    custom.setName(metName.getText().toString());
                    List<List<String>> list = new ArrayList<>();
                    List<String> list0 = new ArrayList<>();
                    list0.add(metName.getText().toString());
                    list0.add(metUrl.getText().toString());
                    list.add(list0);
                    Gson gson = new Gson();
                    custom.setUrl(gson.toJson(list));
                    CustomDbTool.update(custom);

                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setEventId(MessageEvent.EVENT_CUSTOM_EDIT);
                    EventBus.getDefault().post(messageEvent);
                }else{
                    CustomSource custom = new CustomSource();
                    custom.setName(metName.getText().toString());
                    List<List<String>> list = new ArrayList<>();
                    List<String> list0 = new ArrayList<>();
                    list0.add(metName.getText().toString());
                    list0.add(metUrl.getText().toString());
                    list.add(list0);
                    Gson gson = new Gson();
                    custom.setUrl(gson.toJson(list));
                    CustomDbTool.add(custom);

                    EventBus.getDefault().post(new MessageEvent(MessageEvent.EVENT_CUSTOM_ADD));
                }
                finishActivity();
                break;
        }
    }

    public void finishActivity(){
        hideInput();
        finish();
    }


}
