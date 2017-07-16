package com.icecream.images.selector.master;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * app
 * Created by admin on 17/6/22.
 */

public class BaseApplication extends Application {

    public ArrayList<String> checkedList = new ArrayList<>();//选中的图片列表

    public int default_checked_counts = 5;//默认最多能选中图片的个数

    public void setDefault_checked_counts(int default_checked_counts) {
        this.default_checked_counts = default_checked_counts;
    }

    public boolean isCheckedMax() {//选择够5张图片了
        if (default_checked_counts > checkedList.size()) {
            return false;
        }
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initImageLoader(this);
    }

    //初始化imageLoader
    public void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "icecream/Cache");//自定义缓存路径
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.memoryCacheExtraOptions(480, 800);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.threadPoolSize(3);// 线程优先级
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator()); // 将保存的时候的URI名称用MD5
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.defaultDisplayImageOptions(DisplayImageOptions.createSimple());
        config.diskCache(new UnlimitedDiskCache(cacheDir));//自定义缓存路径
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }
}
