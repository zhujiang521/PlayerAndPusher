package com.zj.play;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = PlayActivity.class.getSimpleName();
    private DNPlayer dnPlayer;
    private SurfaceView surfaceView;
    private String url;
    private SeekBar seekBar;
    private boolean isTouch;
    private boolean isSeek;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager
                .LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_play);
        initView();
        Intent intent = getIntent();
        url = intent.getStringExtra("tvUrl");
        initPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dnPlayer.prepare();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                    .LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_play);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        dnPlayer.setSurfaceView(surfaceView);
        dnPlayer.setDataSource(url);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setProgress(progress);

    }


    private void initPlay() {
        dnPlayer = new DNPlayer();
        dnPlayer.setSurfaceView(surfaceView);
        //dnPlayer.setDataSource("rtmp://58.200.131.2:1935/livetv/hunantv");
        //dnPlayer.setDataSource("rtmp://live.hkstv.hk.lxdns.com/live/hks");
        dnPlayer.setDataSource(url);
        //dnPlayer.setDataSource("/sdcard//b.mp4");
        dnPlayer.setOnPrepareListener(new DNPlayer.OnPrepareListener() {
            @Override
            public void onPrepared() {
                //获得时间
                int duration = dnPlayer.getDuration();
                //直播： 时间就是0
                if (duration > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //显示进度条
                            seekBar.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    seekBar.setVisibility(View.GONE);
                }
                dnPlayer.start();
            }
        });
        dnPlayer.setOnErrorListener(new DNPlayer.OnErrorListener() {
            @Override
            public void onError(final int error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PlayActivity.this, "报错了。编号为：" + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dnPlayer.setOnProgressListener(new DNPlayer.OnProgressListener() {

            @Override
            public void onProgress(final int progress2) {
                if (!isTouch) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int duration = dnPlayer.getDuration();
                            //如果是直播
                            if (duration != 0) {
                                if (isSeek) {
                                    isSeek = false;
                                    return;
                                }
                                //更新进度 计算比例
                                seekBar.setProgress(progress2 * 100 / duration);
                            }
                        }
                    });
                }
            }
        });

    }


    public void start(View view) {
        int currentapiVersion = Build.VERSION.SDK_INT;
        Toast.makeText(this, "当前安卓版本：" + currentapiVersion, Toast.LENGTH_SHORT).show();
    }

    public void stop(View view) {
        String CPU_ABI = Build.CPU_ABI;
        Toast.makeText(this, "当前CPU_ABI：" + CPU_ABI, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: 开始执行停止方法");
        dnPlayer.stop();
    }

    private void initView() {
        surfaceView = findViewById(R.id.surfaceView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isTouch = true;
    }

    /**
     * 停止拖动时的回调
     *
     * @param seekBar .
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isSeek = true;
        isTouch = false;
        progress = dnPlayer.getDuration() * seekBar.getProgress() / 100;
        //进度调整
        dnPlayer.seek(progress);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dnPlayer.release();
    }

}


/**
 * 香港卫视: rtmp://live.hkstv.hk.lxdns.com/live/hks1
 * <p>
 * 香港财经 rtmp://202.69.69.180:443/webcast/bshdlive-pc
 * <p>
 * 韩国GoodTV,rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp
 * <p>
 * 韩国朝鲜日报,rtmp://live.chosun.gscdn.com/live/tvchosun1.stream
 * <p>
 * 美国1,rtmp://ns8.indexforce.com/home/mystream
 * <p>
 * 美国2,rtmp://media3.scctv.net/live/scctv_800
 * <p>
 * 美国中文电视,rtmp://media3.sinovision.net:1935/live/livestream
 * <p>
 * 湖南卫视 rtmp://58.200.131.2:1935/livetv/hunantv
 */