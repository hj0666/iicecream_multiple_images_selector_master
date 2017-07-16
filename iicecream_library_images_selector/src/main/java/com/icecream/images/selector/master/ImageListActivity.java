package com.icecream.images.selector.master;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * 图片网格列表
 * Created by HuangJin on 17/2/24.
 */

public class ImageListActivity extends BaseActivity {

    private GridView gridview;

    private TextView tv_action;
    private ImageView tv_return;
    private ImageDto imageItem;
    private List<ImageDto> images;//适配器图片
    private ImageListAdapter adapter;
    private int position = 0;//默认是相册的第一列

    @Override
    public void initView(Context mContext) {
        setContentView(R.layout.ice_image_activity_list_image);
        gridview = (GridView) findViewById(R.id.gridview);
        tv_action = (TextView) findViewById(R.id.tv_action);
        tv_return = (ImageView) findViewById(R.id.iv_back);
        //folder：文件夹 为空显示全部图片 camera:显示拍照的图片
        ImageFolder imageFolder = (ImageFolder) getIntent().getSerializableExtra("folder");
        if (imageFolder == null) {//扫描没有图片
            Cursor mCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, "", null,
                    MediaStore.MediaColumns.DATE_ADDED + " DESC");
            imageFolder = new ImageFolder();
            imageFolder.setDir("/所有图片");
            if (mCursor.moveToFirst()) {
                // LogUtils.e(mCursor.getCount() + "");
                int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                LogUtils.e("-date=" + _date);
                do {
                    // 获取图片的路径
                    //如：/storage/emulated/0/Download/1487055003055.jpg
                    String path = mCursor.getString(_date);
                    imageItem = new ImageDto(path);
                    //设置列表 有就打勾
                    if (BaseApplication.getInstance().checkedList != null && !BaseApplication.getInstance().checkedList.isEmpty()) {
                        imageItem.setChecked(BaseApplication.getInstance().checkedList.contains(path));
                    }
                    imageFolder.images.add(imageItem);//一个文件夹图片列表添加多张图片
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }//扫描结束
        //原imageFolder is null ?（则扫描所有图片 重新赋值）：(从传递过来的数据直接赋值)
        images = imageFolder.images;
        adapter = new ImageListAdapter(this, images);
        gridview.setAdapter(adapter);

        //点击完成
        tv_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }
        });
        //去相片列表
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageListActivity.this, ImageFolderListActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 500);
            }
        });
    }

    @Override
    public void setListener(Context mContext) {

    }

    @Override
    public void setData(Context mContext) {

    }

    @Override
    public void back(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (requestCode == 500 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            int i = bundle.getInt("position", 0);
            if (position == i) {//如果是当前的文件夹
                return;
            } else {
                position = i;
                ImageFolder imageFolder = (ImageFolder) bundle.getSerializable("folder");
                if (imageFolder != null) {
                    final List<ImageDto> images = imageFolder.images;
                    //设置列表 有就打勾
                    if (images != null && !images.isEmpty()) {
                        LogUtils.e("有数据" + this.images.size());
                        this.images.clear();
                        this.images.addAll(images);
                        for (ImageDto item : this.images) {
                            item.setChecked(BaseApplication.getInstance().checkedList.contains(item.path));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
