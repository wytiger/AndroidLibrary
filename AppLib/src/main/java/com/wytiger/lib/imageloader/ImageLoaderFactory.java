package com.wytiger.lib.imageloader;

/**
 * description:
 * Created by wytiger on 2017-2-5.
 */

public class ImageLoaderFactory {
    public static IImageLoader getImageLoader() {

//        return PicassoImageLoader.getInstance();
        return GlideImageLoader.getInstance();
    }

}
