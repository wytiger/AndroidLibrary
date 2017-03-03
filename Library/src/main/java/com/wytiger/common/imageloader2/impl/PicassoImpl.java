package com.wytiger.common.imageloader2.impl;

import com.squareup.picasso.Picasso;
import com.wytiger.common.imageloader2.IImageInterface;

/**
 * description:
 * Created by wytiger on 2017-2-5.
 */
public class PicassoImpl extends IImageInterface {
    private static class SingletonHolder {
        private static final PicassoImpl INSTANCE = new PicassoImpl();
    }

    public static IImageInterface getInstance() {
        return PicassoImpl.SingletonHolder.INSTANCE;
    }

    @Override
    public void display(Builder builder) {
        if (builder.placeholderResId > 0 && builder.errorResId > 0) {
            Picasso.with(builder.context)
                    .load(builder.url)
                    .placeholder(builder.placeholderResId)
                    .into(builder.imageView);
        } else if (builder.placeholderResId > 0) {
            Picasso.with(builder.context)
                    .load(builder.url)
                    .placeholder(builder.placeholderResId)
                    .into(builder.imageView);
        } else if (builder.errorResId > 0) {
            Picasso.with(builder.context)
                    .load(builder.url)
                    .error(builder.errorResId)
                    .into(builder.imageView);
        } else {
            Picasso.with(builder.context)
                    .load(builder.url)
                    .into(builder.imageView);
        }
    }
}
