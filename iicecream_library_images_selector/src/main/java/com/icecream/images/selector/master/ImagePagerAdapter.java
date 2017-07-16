package com.icecream.images.selector.master;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 图片滑动适配器 全屏显示 可放大
 * Created by admin on 2016/10/26.
 */
public class ImagePagerAdapter extends PagerAdapter {

    //装ImageView数组
    private List<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public ImagePagerAdapter(Context context, List<String> content) {
        this.mContext = context;
        for (int i = 0; i < content.size(); i++) {
            String url = content.get(i);
            if (!TextUtils.isEmpty(url)) {
                this.mImageUrls.add(i, url);
            }
        }

    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        ImageUtils.displayImage(mImageUrls.get(position), photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                ((Activity)mContext).finish();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}