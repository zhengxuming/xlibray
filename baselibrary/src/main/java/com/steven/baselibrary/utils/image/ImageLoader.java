package com.steven.baselibrary.utils.image;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.steven.baselibrary.R;

/**
 * Created by Steven on 2017/7/11.
 */

public class ImageLoader {

    public static ImageLoaderOption option;

    public ImageLoader() {

    }

    public static void showImage(Context ctx,View imageView, Object path) {
        RequestOptions options = new RequestOptions();
        options.centerCrop().placeholder(R.mipmap.image_selector_placeholder).error(R.mipmap.image_selector_placeholder);
        Glide.with(ctx).load(path+"/150x150").apply(options).into((ImageView) imageView);
    }

    public static void showImage(Context ctx,View imageView, Object path, int placeholder) {
        RequestOptions options = new RequestOptions();
        options.centerCrop().placeholder(placeholder).error(placeholder);
        Glide.with(ctx).load(path+"/150x150").apply(options).into((ImageView) imageView);
    }

    public static void showOrigionImage(Context ctx,View imageView, Object path) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.image_selector_placeholder).error(R.mipmap.image_selector_placeholder);
        Glide.with(ctx).load(path).apply(options).into((ImageView) imageView);
    }

    public static void showOrigionImage(Context ctx,View imageView, Object path, int placeholder) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder).error(placeholder);
        Glide.with(ctx).load(path).apply(options).into((ImageView) imageView);
    }

    public static void showRoundCornerImg(Context ctx,View imageView, Object path,int radius, int placeholder) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder).error(placeholder);
        options.centerCrop().transform(new GlideRoundTransform(ctx,radius));
        Glide.with(ctx).load(path).apply(options).into((ImageView) imageView);
    }

    public static void showRoundCornerImg(Context ctx,View imageView, Object path,int radius) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.image_selector_placeholder).error(R.mipmap.image_selector_placeholder);
        options.centerCrop().transform(new GlideRoundTransform(ctx,radius));
        Glide.with(ctx).load(path).apply(options).into((ImageView) imageView);
    }

    public static void showCircleImage(Context ctx,View imageView, Object path, int placeholder) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder).error(placeholder);
        options.centerCrop().circleCrop();
        Glide.with(ctx).load(path).apply(options).into((ImageView) imageView);
    }

}
