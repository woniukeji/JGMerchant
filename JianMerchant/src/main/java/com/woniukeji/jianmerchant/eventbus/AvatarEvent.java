package com.woniukeji.jianmerchant.eventbus;

import android.graphics.Bitmap;

/**
 * Created by invinjun on 2015/10/12.
 */
public class AvatarEvent {
 Bitmap bitmap;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
