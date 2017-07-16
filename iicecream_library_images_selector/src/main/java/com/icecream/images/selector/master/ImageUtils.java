package com.icecream.images.selector.master;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageUtils {

    /**
     * 显示网络图片
     *
     * @param uri
     * @param imageView
     */
    public static void displayImage(String uri, ImageView imageView) {
        if (TextUtils.isEmpty(uri)) return;
        ImageLoader.getInstance().displayImage(
                uri,
                imageView,
                //new DisplayImageOptions.Builder()
                // 这个一定要设置，不设置的话会导致图片不能够铺满整个控件，这个是设置圆角效果的，如果大家不喜欢圆角可以设置为1几乎没有什么效果
                new DisplayImageOptions.Builder()
                        //.showImageOnLoading(R.drawable.ic_launcher) //设置图片在下载期间显示的图片
                        //.showImageForEmptyUri(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                        //.showImageOnFail(R.drawable.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                        .cacheOnDisk(true)
                        .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                        .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                        .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                        .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                        .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                        .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                        .build());//构建完成
    }

    /**
     * 显示网络图片
     *
     * @param uri
     * @param imageView
     */
    public static void displayImageListener(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView,
                new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {

                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1,
                                                FailReason arg2) {

                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1,
                                                  Bitmap arg2) {

                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {

                    }
                });

    }


    public static void showImageActivity(Context mContext, ImageListDto data) {
        if (mContext == null || data == null) return;
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        intent.putExtra("data", data);
        mContext.startActivity(intent);
       // ((Activity) mContext).overridePendingTransition(R.anim.ice_image_dialog_anim_out, R.anim.ice_image_dialog_anim_in);
    }

    public static String getCameraPath() {
        return Environment
                .getExternalStorageDirectory() + "/icecream/";
    }
}
