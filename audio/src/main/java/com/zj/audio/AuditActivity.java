package com.zj.audio;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zj.audio.live.LivePusher;


public class AuditActivity extends AppCompatActivity {

    private LivePusher livePusher;
    private String url;
    private int width;
    private int height;
    private int bitrate;
    private int fps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        url = getIntent().getStringExtra("liveUrl");
        width = getIntent().getIntExtra("width",800);
        height = getIntent().getIntExtra("height",480);
        bitrate = getIntent().getIntExtra("bitrate",800_000);
        fps = getIntent().getIntExtra("fps",10);
        livePusher = new LivePusher(this, width, height, bitrate, fps, Camera.CameraInfo.CAMERA_FACING_BACK);
        //  设置摄像头预览的界面
        livePusher.setPreviewDisplay(surfaceView.getHolder());
    }

    public void switchCamera(View view) {
        livePusher.switchCamera();
    }

    public void startLive(View view) {

        livePusher.startLive(url);
    }

    public void stopLive(View view) {
        livePusher.stopLive();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        livePusher.release();
    }
}
