package com.icecream.images.selector.master;


import java.io.Serializable;

/**
 * 图片
 * Created by admin on 17/2/23.
 */

public class ImageDto implements Serializable {

    String path;//一张图片的路径
    private boolean checked;//选中

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageDto(String p) {
        this.path = p;
    }

}