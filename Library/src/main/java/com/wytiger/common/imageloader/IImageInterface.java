package com.wytiger.common.imageloader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import java.io.File;

/**
 * description:
 * Created by wytiger on 2017-2-5.
 */

public interface IImageInterface {
   void displayImage(ImageView imageView, String url);
   void displayImage(ImageView imageView, File file);
   void displayImage(ImageView imageView, Uri uri);

    void displayImage(Context context, ImageView imageView, String url);
    void displayImage(Activity activity, ImageView imageView, String url);
    void displayImage(Fragment fragment, ImageView imageView, String url);
    void displayImage(Context context, ImageView imageView, String url,int placeholderResId, int errorResId);

}
