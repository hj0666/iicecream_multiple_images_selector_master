package com.icecream.images.selector.master;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 展示图片 内容页面 发布的图片
 * Created by admin on 2016/10/28.
 */
public class MyGridAdapter extends BaseAdapter {

    private final ImagePhotoDialog photoDialog;
    public ArrayList<String> selectedList = new ArrayList<>();
    private Context mContext;
    private ImageItemDeleteListener updateListener;
    public String PicName;

    public String getPicName() {
        return PicName;
    }

    public void setPicName(String picName) {
        PicName = picName;
    }


    public MyGridAdapter(Context context, ArrayList<String> allSelectedPicture) {
        this.selectedList = allSelectedPicture;
        this.mContext = context;
        photoDialog = new ImagePhotoDialog(context);
    }

    public void setImageReMoveListener(ImageItemDeleteListener updateListener) {
        this.updateListener = updateListener;
    }

    @Override
    public int getCount() {
        return selectedList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.ice_image_item_mygridview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(position);

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;//显示的图片
        public ImageView iv_add;//显示加号图片
        public ImageView btn;//显示删除按键

        public ViewHolder(View view) {
            findViews(view);
        }

        public void findViews(View view) {
            image = (ImageView) view.findViewById(R.id.iv);
            iv_add = (ImageView) view.findViewById(R.id.iv_add);
            int screenWidth = getScreenWidth(mContext);
            ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
            ViewGroup.LayoutParams layoutParams_add = iv_add.getLayoutParams();
            layoutParams.width = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin_16) * 4) / 3;
            layoutParams.height = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin_16) * 4) / 3;
            layoutParams_add.width = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin_16) * 4) / 3;
            layoutParams_add.height = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin_16) * 4) / 3;
            btn = (ImageView) view.findViewById(R.id.child_delete);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        public void setData(final int position) {
            // LogUtils.e("position=" + position + "---selectedList.size()=" + selectedList.size());
            if (position == selectedList.size()) {
                //添加图片
                image.setVisibility(View.GONE);//内容图片隐藏
                iv_add.setVisibility(View.VISIBLE);//显示加号图片
                btn.setVisibility(View.GONE);//删除图片隐藏
                iv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photoDialog.dialogShow(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                photoDialog.dismiss();
                                goCamera((Activity) mContext);
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectClick((Activity) mContext, selectedList);
                                photoDialog.dismiss();
                            }
                        });
                    }
                });
                //超过5张图片
                if (selectedList.size() == BaseApplication.getInstance().default_checked_counts) {
                    iv_add.setVisibility(View.GONE);//加号图片隐藏
                    image.setVisibility(View.GONE);//内容图片显示
                    btn.setVisibility(View.GONE);//删除按键显示
                }
            } else {
                //显示图片
                iv_add.setVisibility(View.GONE);//加号图片隐藏
                image.setVisibility(View.VISIBLE);//内容图片显示
                btn.setVisibility(View.VISIBLE);//删除按键显示
                ImageLoader.getInstance().displayImage("file://" + selectedList.get(position),
                        image);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击后移除图片
                        updateListener.onItemDelete(position);
                    }
                });
            }
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageListDto iceData = new ImageListDto();
                    iceData.setId(position);
                    iceData.setShow(true);
                    List<String> pictureList = new ArrayList<>();
                    for (String str : selectedList) {
                        String s = "file://" + str;
                        pictureList.add(s);
                    }
                    iceData.setImageList(pictureList);
                    ImageUtils.showImageActivity(mContext, iceData);
                }
            });

        }
    }

    /**
     * 点击 加号 去图片网格选择图片
     *
     * @param mContext
     * @param selectedList
     */
    private void selectClick(Activity mContext, ArrayList<String> selectedList) {
        Intent intent = new Intent();
        intent.setClass(mContext, ImageListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("counts", BaseApplication.getInstance().default_checked_counts);
        // ToastUtils.showToastByShort("目前已经选了" + selectedList.size() + "张");
        bundle.putStringArrayList("data", selectedList);
        intent.putExtras(bundle);
        mContext.startActivityForResult(intent, 600);
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

    /**
     * 使用相机拍照
     */
    protected void goCamera(Activity mContext) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //以系统时间作为该文件 民命
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String picName = formatter.format(curDate);
        setPicName(picName);
        picName = formatter.format(curDate);
        File picture = new File(ImageUtils.getCameraPath(), picName + ".jpg");
        //ToastUtils.showShort(file.getPath());
        Uri uri = Uri.fromFile(picture);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mContext.startActivityForResult(intent, 800);
    }

    /**
     * 发布图片列表中 移除图片
     */
    public interface ImageItemDeleteListener {
        void onItemDelete(int position);
    }

}

