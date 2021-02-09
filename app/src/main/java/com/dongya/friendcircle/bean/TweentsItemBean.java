package com.dongya.friendcircle.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * FileName: TweentsItemBean
 * Author: dongya
 * Date: 2021/2/7 3:45 PM
 * Description:
 */
public class TweentsItemBean implements MultiItemEntity {

    private int type;
    private String name;
    private String avatar;
    private String content;
    private List<TweetsBean.ImagesBean> imageList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<TweetsBean.ImagesBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<TweetsBean.ImagesBean> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
