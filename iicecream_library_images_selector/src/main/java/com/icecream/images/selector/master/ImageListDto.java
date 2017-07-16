package com.icecream.images.selector.master;

import java.io.Serializable;
import java.util.List;

/**
 * 传递的图片urls
 * Created by admin on 17/4/10.
 */

public class ImageListDto implements Serializable {
    private List<String> imageList;//图片路径
    private Boolean show;
    private int id;

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
