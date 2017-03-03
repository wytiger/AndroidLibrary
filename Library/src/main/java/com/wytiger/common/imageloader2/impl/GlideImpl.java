package com.wytiger.common.imageloader2.impl;

import com.bumptech.glide.Glide;
import com.wytiger.common.imageloader2.IImageInterface;

/**
 * Created by wytiger at 2017/3/3.
 */

public class GlideImpl extends IImageInterface {
    private static class SingletonHolder {
        private static final GlideImpl INSTANCE = new GlideImpl();
    }

    public static IImageInterface getInstance() {
        return GlideImpl.SingletonHolder.INSTANCE;
    }

    @Override
    public void display(Builder builder) {
        if (builder.placeholderResId > 0 && builder.errorResId > 0) {
            Glide.with(builder.context)
                    .load(builder.url)
                    .placeholder(builder.placeholderResId)
                    .into(builder.imageView);
        } else if (builder.placeholderResId > 0) {
            Glide.with(builder.context)
                    .load(builder.url)
                    .placeholder(builder.placeholderResId)
                    .into(builder.imageView);
        } else if (builder.errorResId > 0) {
            Glide.with(builder.context)
                    .load(builder.url)
                    .error(builder.errorResId)
                    .into(builder.imageView);
        } else {
            Glide.with(builder.context)
                    .load(builder.url)
                    .into(builder.imageView);
        }
    }
}
