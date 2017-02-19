package com.wytiger.common.imageloader.loader;

/**
 * description:
 * Created by wytiger on 2017-2-5.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wytiger.common.imageloader.IImageInterface;

import java.io.File;

/**
 * Picasso.with(myFragment)
 * .load(url)
 * .into(myImageView);
 */
public class PicassoImageLoader implements IImageInterface {


    private static class SingletonHolder {
        private static final PicassoImageLoader INSTANCE = new PicassoImageLoader();
    }

    public static IImageInterface getInstance() {
        return PicassoImageLoader.SingletonHolder.INSTANCE;
    }

    @Override
    public void displayImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    @Override
    public void displayImage(ImageView imageView, File file) {
        Picasso.with(imageView.getContext())
                .load(file)
                .into(imageView);
    }

    @Override
    public void displayImage(ImageView imageView, Uri uri) {
        Picasso.with(imageView.getContext())
                .load(uri)
                .into(imageView);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void displayImage(Activity activity, ImageView imageView, String url) {
        Picasso.with(activity)
                .load(url)
                .into(imageView);
    }

    @TargetApi(11)
    @Override
    public void displayImage(Fragment fragment, ImageView imageView, String url) {
        Picasso.with(fragment.getActivity())
                .load(url)
                .into(imageView);
    }

    @Override
    public void displayImage(android.support.v4.app.Fragment fragmentV4, ImageView imageView, String url) {
        Picasso.with(fragmentV4.getActivity())
                .load(url)
                .into(imageView);
    }
}
