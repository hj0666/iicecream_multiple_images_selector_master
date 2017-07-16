package com.icecream.images.selector.master;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹类
 * Created by admin on 17/2/23.
 */

public class ImageFolder implements Serializable{
    /**
     * 图片的文件夹路径
     */
    private String dir;

    /**
     * 第一张图片的路径
     */
    private String firstImagePath;
    /**
     * 文件夹的名称
     */
    private String name;

    /**
     * 图片列表
     */
    public List<ImageDto> images = new ArrayList<>();

    public int checkedCounts=0;//当前文件夹包含选择中图片的个数

    public int getCheckedCounts() {
        return checkedCounts;
    }

    public void setCheckCounts(int checkedCounts) {
        this.checkedCounts = checkedCounts;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf);
        this.name = this.name.replace("/", "");
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

}
