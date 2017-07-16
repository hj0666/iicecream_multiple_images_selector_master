package com.icecream.images.selector.master;

import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示风格图片 图片列表
 * Created by admin on 17/2/24.
 */

public class ImageListAdapter extends BaseAdapter {

    /**
     * 图片列表
     */
    private List<ImageDto> iceImages = new ArrayList<>();
    //已选择的图片
    //  private IceImageListListener adapterListener;
    private Context mContext;

//    public void setIceImageListListener(IceImageListListener adapterListener) {
//        this.adapterListener = adapterListener;
//    }

    public ImageListAdapter(Context mContext, List<ImageDto> images) {
        this.mContext = mContext;
        this.iceImages = images;
    }

    @Override
    public int getCount() {
        return iceImages.size();
    }

    @Override
    public Object getItem(int position) {
        return iceImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.ice_image_item_gridview, null);
            viewHolder = new ImageViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ImageViewHolder) convertView.getTag();
        }
        final ImageDto item = iceImages.get(position);
        viewHolder.setData(item, position);
        return convertView;
    }


    class ImageViewHolder {
        public ImageView image;
        public ImageView iv_check;//真正的打勾事件
        public CheckBox check;//打勾状态
        public TextView mask;//蒙板

        public ImageViewHolder(View convertView) {
            initView(convertView);
        }

        private void initView(View convertView) {
            iv_check = (ImageView) convertView.findViewById(R.id.iv_check);
            image = (ImageView) convertView.findViewById(R.id.iv);
            check = (CheckBox) convertView.findViewById(R.id.check);
            mask = (TextView) convertView.findViewById(R.id.mask);
            int screenWidth = getScreenWidth(mContext);
            ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
            ViewGroup.LayoutParams layoutParams_mask = mask.getLayoutParams();
            layoutParams.width = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin_8) * 5) / 4;
            layoutParams.height = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin_8) * 5) / 4;
            //蒙板的布局
            layoutParams_mask.width = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin_8) * 5) / 4;
            layoutParams_mask.height = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin_8) * 5) / 4;
        }

        private void setData(final ImageDto ice, final int position) {
            ImageUtils.displayImage("file://" + ice.path, image);
            iv_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ice.isChecked()) {//之前没有打勾
                        if (!BaseApplication.getInstance().isCheckedMax()) {//小于5张 还可以打勾
                            iceImages.get(position).setChecked(true);
                            check.setChecked(true);
                            mask.setAlpha(0.5f);
                            LogUtils.e("ad目前选择了图片个数=" + BaseApplication.getInstance().checkedList.size());
//
                            BaseApplication.getInstance().checkedList.add(ice.path);
                        } else {//再点就超过5张了
                            check.setChecked(true);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    check.setChecked(false);//设置选择中又取消的效果
                                }
                            }, 100);
                            String str = mContext.getResources().getString(R.string.selected_picture_text) + BaseApplication.getInstance().default_checked_counts + mContext.getResources().getString(R.string.selected_picture_text2);
                            Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                        }

                    } else {//已经打了勾的
                        iceImages.get(position).setChecked(false);
                        check.setChecked(false);
                        mask.setAlpha(0.0f);
                        //LogUtils.e("ad目前选择了图片个数=" + BaseApplication.getInstance().checkedList.size());
                        BaseApplication.getInstance().checkedList.remove(ice.path);
                    }
                }
            });
            check.setChecked(ice.isChecked());
            mask.setAlpha(ice.isChecked() ? 0.5f : 0.0f);
            //点击查看图片
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageListDto iceData = new ImageListDto();
                    iceData.setId(position);
                    iceData.setShow(false);
                    List<String> pictureList = new ArrayList<>();
                    for (ImageDto str : iceImages) {
                        String s = "file://" + str.path;
                        pictureList.add(s);
                    }
                    iceData.setImageList(pictureList);
                    ImageUtils.showImageActivity(mContext, iceData);
                }
            });
        }
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

}
