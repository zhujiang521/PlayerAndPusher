package com.zj.audio;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

/**
 * 版权：渤海新能 版权所有
 *
 * @author zhujiang
 * 版本：1.5
 * 创建日期：2020/3/3
 * 描述：PlayerAndPusher
 */
public class LiveRecording {

    private final WeakReference<Activity> mActivity;

    private LiveRecording(Activity mActivity) {
        this.mActivity = new WeakReference<>(mActivity);
    }

    public static LiveRecording create(Activity activity) {
        return new LiveRecording(activity);
    }

    public LiveRecordingModel setDataUrl(String dataUrl) {
        return new LiveRecordingModel(dataUrl);
    }

    public class LiveRecordingModel {

        private String dataUrl;
        private int width;
        private int height;
        private int bitrate;
        private int fps;

        LiveRecordingModel(String dataUrl) {
            this.dataUrl = dataUrl;
        }

        /**
         * 设置成像宽度
         * @param width
         * @return
         */
        public LiveRecordingModel setWidth(int width) {
            this.width = width;
            return this;
        }

        /**
         * 设置成像高度
         * @param height
         * @return
         */
        public LiveRecordingModel setHeight(int height) {
            this.height = height;
            return this;
        }

        /**
         * 设置直播比特率
         * @param bitrate
         * @return
         */
        public LiveRecordingModel setBitrate(int bitrate) {
            this.bitrate = bitrate;
            return this;
        }

        /**
         * 设置FPS值
         * @param fps
         * @return
         */
        public LiveRecordingModel setFps(int fps) {
            this.fps = fps;
            return this;
        }

        public void build() {
            if (TextUtils.isEmpty(dataUrl) || dataUrl.equals("")) {
                throw new NullPointerException("dataUrl is not null");
            }
            if (mActivity.get() != null) {
                Intent intent = new Intent(mActivity.get(), AuditActivity.class);
                intent.putExtra("liveUrl", dataUrl);
                intent.putExtra("width", width);
                intent.putExtra("height", height);
                intent.putExtra("bitrate", bitrate);
                intent.putExtra("fps", fps);
                mActivity.get().startActivity(intent);
            }
        }
    }

}
