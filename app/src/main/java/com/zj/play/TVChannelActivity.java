package com.zj.play;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zj.audio.AuditActivity;
import com.zj.audio.LiveRecording;

import java.util.ArrayList;
import java.util.List;

public class TVChannelActivity extends AppCompatActivity {

    private RecyclerView recycleView;
    private List<TVBean> tvBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvchannel);
        initData();
        initView();
    }

    private void initData() {
        tvBeanList.add(new TVBean("测试本地视频","/sdcard/Pictures/test.mp4"));
        tvBeanList.add(new TVBean("香港卫视","rtmp://live.hkstv.hk.lxdns.com/live/hks1"));
        tvBeanList.add(new TVBean("香港财经","rtmp://202.69.69.180:443/webcast/bshdlive-pc"));
        tvBeanList.add(new TVBean("韩国GoodTV","rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp"));
        tvBeanList.add(new TVBean("韩国朝鲜日报","rtmp://live.chosun.gscdn.com/live/tvchosun1.stream"));
        tvBeanList.add(new TVBean("美国1","rtmp://ns8.indexforce.com/home/mystream"));
        tvBeanList.add(new TVBean("美国2","rtmp://media3.scctv.net/live/scctv_800"));
        tvBeanList.add(new TVBean("美国中文电视","rtmp://media3.sinovision.net:1935/live/livestream"));
        tvBeanList.add(new TVBean("湖南卫视","rtmp://58.200.131.2:1935/livetv/hunantv"));
        tvBeanList.add(new TVBean("自己推流测试","rtmp://106.13.175.12/myapp/mystream"));
    }

    private void initView() {
        recycleView = findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new  LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(new CommonAdapter<TVBean>(this,R.layout.adapter_tv_channel,tvBeanList) {
            @Override
            protected void convert(ViewHolder holder, final TVBean tvBean, int position) {
                TextView tvTvName = holder.getView(R.id.tvTvName);
                tvTvName.setText(tvBean.getName());
                tvTvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BroadcastLive.create(TVChannelActivity.this).setDataUrl(tvBean.getUrl()).build();
                    }
                });
            }
        });
    }

    public void startAudit(View view) {
        LiveRecording.create(this).setDataUrl("rtmp://106.13.175.12/myapp/mystream").build();
    }
}
