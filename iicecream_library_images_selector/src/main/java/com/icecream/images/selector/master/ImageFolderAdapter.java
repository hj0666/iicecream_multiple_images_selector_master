package com.icecream.images.selector.master;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹适配器
 * Created by admin on 17/2/23.
 */

public class ImageFolderAdapter extends BaseAdapter {
    private Context mContext;
    private List<ImageFolder> mDirPaths = new ArrayList<>();

    public ImageFolderAdapter(Context mContext, List<ImageFolder> list) {
        this.mContext = mContext;
        this.mDirPaths = list;
    }

    @Override
    public int getCount() {
        return mDirPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return mDirPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FolderViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.ice_image_item_list_dir, null);
            holder = new FolderViewHolder();
            holder.id_dir_item_image = (ImageView) convertView.findViewById(R.id.id_dir_item_image);
            holder.id_dir_item_name = (TextView) convertView.findViewById(R.id.id_dir_item_name);
            holder.id_dir_item_count = (TextView) convertView.findViewById(R.id.id_dir_item_count);
            holder.tv_counts = (TextView) convertView.findViewById(R.id.tv_counts);
            convertView.setTag(holder);
        } else {
            holder = (FolderViewHolder) convertView.getTag();
        }
        final ImageFolder image = mDirPaths.get(position);

        ImageUtils.displayImage("file://" + image.getFirstImagePath(), holder.id_dir_item_image);
        holder.id_dir_item_count.setText("共 " + String.valueOf(image.images.size()) + " 张");
        holder.id_dir_item_name.setText(image.getName());
        int counts = image.getCheckedCounts();
        if (counts > 0) {
            if (counts < 10) {
                holder.tv_counts.setText("0" + counts);
            } else {
                holder.tv_counts.setText("" + counts);
            }
            holder.tv_counts.setVisibility(View.VISIBLE);
        } else {
            holder.tv_counts.setText("");
            holder.tv_counts.setVisibility(View.INVISIBLE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(mContext, ImageListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putSerializable("folder", mDirPaths.get(position));//一个文件夹的所有图片
                data.putExtras(bundle);
                LogUtils.e("position", position + "");
                ((Activity) mContext).setResult(Activity.RESULT_OK, data);
                ((Activity) mContext).finish();
                //for (int i = 0; i < mDirPaths.get(position).images.size(); i++) {
                //  LogUtils.e("当前文件夹下的所有图片：" + mDirPaths.get(position).images.get(i).path);
                //}
            }
        });
        return convertView;
    }

    class FolderViewHolder {
        ImageView id_dir_item_image;//首张图片
        TextView id_dir_item_name;//文件夹名
        TextView id_dir_item_count;//图片个数
        TextView tv_counts;//当前文件夹选中图片个数
    }
}