package com.wytiger.common.imageloader.loader;

/**
 * description:
 * Created by wytiger on 2017-2-5.
 */

import android.content.Context;
import android.widget.ImageView;

import com.wytiger.common.imageloader.IImageLoader;

/**
 * Picasso.with(myFragment)
 .load(url)
 .into(myImageView);
 */
public class PicassoImageLoader implements IImageLoader {


    private static class SingletonHolder {
        private static final PicassoImageLoader INSTANCE = new PicassoImageLoader();
    }

    public static IImageLoader getInstance() {
        return PicassoImageLoader.SingletonHolder.INSTANCE;
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
