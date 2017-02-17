package com.wytiger.common.imageloader;

import com.wytiger.common.imageloader.loader.GlideImageLoader;

/**
 * description:
 * Created by wytiger on 2017-2-5.
 */

public class ImageLoader {
    public static IImageLoader getImageLoader() {

//        return PicassoImageLoader.getInstance();
        return GlideImageLoader.getInstance();
    }

}
