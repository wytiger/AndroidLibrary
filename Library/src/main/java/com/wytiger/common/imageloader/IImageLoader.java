package com.wytiger.common.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * description:
 * Created by wytiger on 2017-2-5.
 */

public interface IImageLoader {
    IImageLoader with(Context context);
    IImageLoader load(String url);
    IImageLoader into(ImageView imageView);
}
