package com.zj.play;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * 版权：渤海新能 版权所有
 *
 * @author zhujiang
 * 版本：1.5
 * 创建日期：2020/3/3
 * 描述：PlayerAndPusher
 */
public class BroadcastLive {

    private final WeakReference<Activity> mActivity;

    private BroadcastLive(Activity mActivity) {
        this.mActivity = new WeakReference<>(mActivity);
    }

    public static BroadcastLive create(Activity activity) {
        return new BroadcastLive(activity);
    }

    public BroadcastLiveModel setDataUrl(String dataUrl) {
        return new BroadcastLiveModel(dataUrl);
    }

    protected class BroadcastLiveModel {
        private String dataUrl;

        BroadcastLiveModel(String dataUrl) {
            this.dataUrl = dataUrl;
        }

        public void build() {
            if (TextUtils.isEmpty(dataUrl) || dataUrl.equals("")) {
                throw new NullPointerException("dataUrl is not null");
            }
            if (mActivity.get() != null) {
                Intent intent = new Intent(mActivity.get(), PlayActivity.class);
                intent.putExtra("tvUrl", dataUrl);
                mActivity.get().startActivity(intent);
            }
        }
    }

}
