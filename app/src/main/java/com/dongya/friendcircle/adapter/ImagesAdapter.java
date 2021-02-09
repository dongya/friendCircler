package com.dongya.friendcircle.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dongya.friendcircle.R;
import com.dongya.friendcircle.bean.TweentsItemBean;
import com.dongya.friendcircle.bean.TweetsBean;

import java.util.List;

/**
 * FileName: ImagesAdapter
 * Author: dongya
 * Date: 2021/2/7 4:27 PM
 * Description:
 */
public class ImagesAdapter extends BaseQuickAdapter<TweetsBean.ImagesBean, BaseViewHolder> {

    public ImagesAdapter(@Nullable List<TweetsBean.ImagesBean> data) {
        super(R.layout.item_image,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TweetsBean.ImagesBean item) {
        ImageView imageView = helper.getView(R.id.image);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.discovery_img_2)
                .error(R.mipmap.discovery_img_2).centerCrop();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = layoutParams.width;
        imageView.setLayoutParams(layoutParams);
        Glide.with(mContext).applyDefaultRequestOptions(options).load(item.getUrl()).into(imageView);
    }
}
