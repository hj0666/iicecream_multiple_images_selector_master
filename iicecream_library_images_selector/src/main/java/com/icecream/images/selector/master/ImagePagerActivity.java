package com.icecream.images.selector.master;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.List;

/**
 * 图片 查看
 * Created by HuangJin on 2016/10/24.
 */
public class ImagePagerActivity extends BaseActivity implements View.OnClickListener {

    private ImagePagerAdapter adapter;
    private LinearLayout viewGroup;
    private ImageViewPager viewpager;
    private ImageView[] tips;
    private boolean isShow = false;

    @Override
    public void initView(Context mContext) {
        // 隐藏android系统的状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 隐藏应用程序的标题栏，即当前activity的标题栏 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ice_image_activity_image_pager);

        viewGroup = (LinearLayout) findViewById(R.id.viewGroup);
        viewpager = (ImageViewPager) findViewById(R.id.view_pager);
        ImageListDto data = (ImageListDto) this.getIntent().getSerializableExtra("data");

        List<String> urls = data.getImageList();
        int position = data.getId();
        isShow = data.getShow();
        if (urls != null && !urls.isEmpty()) {
            if(isShow){
                if (urls.size() > 1) {//一张不显示圆点
                    addIndrcators(mContext, urls.size());
                }
            }
            adapter = new ImagePagerAdapter(mContext, urls);
            viewpager.setAdapter(adapter);
            viewpager.setCurrentItem(position);
            setImageBackground(position);
            viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    setImageBackground(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }

    @Override
    public void setListener(Context mContext) {

    }

    @Override
    public void setData(Context mContext) {

    }

    @Override
    public void back(View view) {
        finish();
    }
    @Override
    public void finish() {
        super.finish();
       // overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 将点点加入到ViewGroup中
     *
     * @param mContext
     * @param length
     */
    private void addIndrcators(Context mContext, int length) {

        tips = new ImageView[length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            tips[i] = imageView;
            tips[i].setImageResource(R.drawable.ice_image_corner_indicator_images);
            if (i == 0) {
                tips[i].setEnabled(true);
            } else {
                tips[i].setEnabled(false);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 16;
            layoutParams.rightMargin = 16;
            viewGroup.addView(imageView, layoutParams);
        }
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        if (tips != null && tips.length > 0)
            for (int i = 0; i < tips.length; i++) {
                if (i == selectItems) {
                    tips[i].setEnabled(true);
                } else {
                    tips[i].setEnabled(false);
                }
            }
    }
}
