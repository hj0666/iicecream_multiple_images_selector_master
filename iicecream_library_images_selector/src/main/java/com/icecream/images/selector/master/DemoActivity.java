package com.icecream.images.selector.master;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;


import java.io.File;
import java.util.ArrayList;

/**
 * 添加图片到gridView
 * Created by admin on 17/3/21.
 */

public class DemoActivity extends AppCompatActivity implements MyGridAdapter.ImageItemDeleteListener {
    private MyGridView gridView;

    private ArrayList<String> dataList = new ArrayList<>(); //存放所有选择的照片
    private MyGridAdapter gridAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphoto);
        gridView = (MyGridView) findViewById(R.id.gridview);
        gridAdapter = new MyGridAdapter(this, dataList);
        gridAdapter.setImageReMoveListener(this);
        gridView.setAdapter(gridAdapter);
        BaseApplication.getInstance().setDefault_checked_counts(6);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 拍照
        if (resultCode == RESULT_OK && requestCode == 800) {
            // 设置文件保存路径
            String name = gridAdapter.getPicName();
            if (!TextUtils.isEmpty(name)) {
                LogUtils.e("图片", name);
            } else {
                LogUtils.e("AB");
            }
            File picture = new File(ImageUtils.getCameraPath(), name + ".jpg");
            if (picture != null) {
                String cameraPath = Uri.fromFile(picture).getPath();
                LogUtils.e(cameraPath);
                dataList.add(cameraPath);
                BaseApplication.getInstance().checkedList.add(cameraPath);//添加到全局变量
                gridAdapter.notifyDataSetChanged();
            }

        }
        if (data == null) return;
        if (resultCode == RESULT_OK && requestCode == 600) {
            if (BaseApplication.getInstance().checkedList != null
                    && !BaseApplication.getInstance().checkedList.isEmpty()) {
                dataList.clear();
                dataList.addAll(BaseApplication.getInstance().checkedList);
                gridAdapter.notifyDataSetChanged();
                }
            }

    }

    @Override
    public void onItemDelete(final int position) {
        dataList.remove(position);
        BaseApplication.getInstance().checkedList.remove(position);
        //  tv_pictures.setText(allSelectedPicture.size() + "/5");
        gridAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity退出后移除所有选中的图片 防止别处添加图片时混乱
        BaseApplication.getInstance().checkedList.clear();
    }
}
