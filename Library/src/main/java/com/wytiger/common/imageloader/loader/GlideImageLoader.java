package com.wytiger.common.imageloader.loader;

/**
 * description:
 * Created by wytiger on 2017-2-5.
 */

import android.content.Context;
import android.widget.ImageView;

import com.wytiger.common.imageloader.IImageLoader;

/**
 * Glide.with(myFragment)
 .load(url)
 .into(myImageView);
 */
public class GlideImageLoader implements IImageLoader {
    private static class SingletonHolder {
        private static final GlideImageLoader INSTANCE = new GlideImageLoader();
    }

    public static IImageLoader getInstance() {
        return GlideImageLoader.SingletonHolder.INSTANCE;
    }
    @Override
    public IImageLoader with(Context context) {
        return null;
    }

    @Override
    public IImageLoader load(String url) {
        return null;
    }

    @Override
    public IImageLoader into(ImageView imageView) {
        return null;
    }

}
