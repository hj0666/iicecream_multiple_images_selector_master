package com.icecream.images.selector.master;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ImageFolderListActivity extends BaseActivity {

    private HashMap<String, Integer> tmpDir = new HashMap<>();//一对多个文件夹 临时的辅助类，用于防止同一个文件夹的多次扫描
    private ArrayList<ImageFolder> mDirPaths = new ArrayList<>();//文件夹列表
    private ImageFolder imageTopFolder;//列表的第一个文件夹(表示：/所有图片)


    @Override
    public void initView(Context mContext) {
        setContentView(R.layout.ice_image_activity_list_folder);
        Cursor mCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns.DATA}, "", null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");
        imageTopFolder = new ImageFolder();
        imageTopFolder.setDir("/所有图片");
        mDirPaths.add(imageTopFolder);//手动添加文件夹第一项 所有图片
        if (mCursor.moveToFirst()) {
            LogUtils.e(mCursor.getCount() + "");
            int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            LogUtils.e("-date=" + _date);
            int i = 0;
            do {
                i++;
                // 获取图片的路径
                //如：/storage/emulated/0/Download/1487055003055.jpg
                String path = mCursor.getString(_date);
                if (i == 1) {
                    imageTopFolder.setFirstImagePath(path);//设置首张图片路径
                }
                // LogUtils.e("-path="+path);
                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    continue;
                }
                ImageFolder imageFolder;
                //文件夹 完整路径
                String dirPath = parentFile.getAbsolutePath();
               // LogUtils.e("-dirPath=" + dirPath);

                if (!tmpDir.containsKey(dirPath)) {
                    // 初始化imageFolder
                    imageFolder = new ImageFolder();//除第一个(所有图片的文件夹)分类的文件夹
                    imageFolder.setDir(dirPath);//设置文件夹路径
                    imageFolder.setFirstImagePath(path);//设置首张图片路径

                    mDirPaths.add(imageFolder);//把文件夹添加进列表里
                    //一个文件夹完整路径对应一个文件夹的索引
                    tmpDir.put(dirPath, mDirPaths.indexOf(imageFolder));//集合添加一个文件夹
                } else {
                    //已经存在了这个文件夹 就从列表根据所索获取对象
                    imageFolder = mDirPaths.get(tmpDir.get(dirPath));//表示同一个文件夹
                }
                //设置文件夹选中的图片的个数
               // LogUtils.e("文件夹名："+dirPath);
                if (BaseApplication.getInstance().checkedList != null && !BaseApplication.getInstance().checkedList.isEmpty()) {
                    if (BaseApplication.getInstance().checkedList.contains(path)) {
                        imageFolder.setCheckCounts(imageFolder.getCheckedCounts()+1);
                      //  LogUtils.e(dirPath+"-imageFolder.getCheckedCounts()+1="+(imageFolder.getCheckedCounts()+1));
                    }else{
                       // LogUtils.e(dirPath+"-没有选择中");
                    }
                }

                imageFolder.images.add(new ImageDto(path));//一个文件夹图片列表添加多张图片
                imageTopFolder.images.add(new ImageDto(path));//显示所有图片的文件夹：添加所有图片
            } while (mCursor.moveToNext());
           // LogUtils.e("i=" + i);
        }
        mCursor.close();
        imageTopFolder.setCheckCounts(BaseApplication.getInstance().checkedList.size());//所有图片文件夹中所有选中图片的个数
        ListView lv_data = (ListView) findViewById(R.id.lv_data);
        ImageFolderAdapter adapter = new ImageFolderAdapter(this, mDirPaths);
        lv_data.setAdapter(adapter);
    }

    @Override
    public void setListener(Context mContext) {

    }

    @Override
    public void setData(Context mContext) {

    }

    public void back(View v) {
        finish();
    }
}
