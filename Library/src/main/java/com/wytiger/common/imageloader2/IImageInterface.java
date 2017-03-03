package com.wytiger.common.imageloader2;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by tiger
 * 2017/3/3 0003.
 */

public abstract class IImageInterface {
    public abstract void display(Builder builder);

    public static class Builder {
        public Context context;
        public ImageView imageView;
        public String url;
        public int placeholderResId;
        public int errorResId;

        public Builder(Context context, ImageView imageView, String url, int placeholderResId, int errorResId) {
            this.context = context;
            this.imageView = imageView;
            this.url = url;
            this.placeholderResId = placeholderResId;
            this.errorResId = errorResId;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder placeholder(int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        public Builder error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }


        public Builder build() {

            return this;
        }
    }
}
