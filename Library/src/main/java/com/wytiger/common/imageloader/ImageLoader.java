package com.wytiger.common.imageloader;

import com.wytiger.common.imageloader.loader.GlideImageLoader;

/**
 * description:策略模式封装图片加载框架
 * Created by wytiger on 2017-2-5.
 */

public class ImageLoader {
    public static IImageInterface getImageLoader() {

//        return PicassoImageLoader.getInstance();
        return GlideImageLoader.getInstance();
    }

}
