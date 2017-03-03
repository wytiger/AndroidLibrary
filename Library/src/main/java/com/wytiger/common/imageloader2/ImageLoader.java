package com.wytiger.common.imageloader2;

import com.wytiger.common.imageloader2.impl.GlideImpl;

/**
 * Created by tiger
 * 2017/3/3.
 */

public class ImageLoader {
    public static IImageInterface getImageLoader() {

//        return PicassoImpl.getInstance();
        return GlideImpl.getInstance();
    }


}
