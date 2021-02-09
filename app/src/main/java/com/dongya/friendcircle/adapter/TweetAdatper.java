package com.dongya.friendcircle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dongya.friendcircle.R;
import com.dongya.friendcircle.bean.TweentsItemBean;
import com.dongya.friendcircle.popwindow.CustomAttachPopup;
import com.dongya.friendcircle.utils.ScreenUtil;
import com.dongya.friendcircle.view.ExpandTextView;
import com.dongya.friendcircle.view.NineView;
import com.lxj.xpopup.XPopup;

import java.util.List;

/**
 * FileName: TweetAdatper
 * Author: dongya
 * Date: 2021/2/7 3:35 PM
 * Description:
 */
public class TweetAdatper extends BaseMultiItemQuickAdapter<TweentsItemBean,BaseViewHolder> {

    public static final int  Item_header = 0;
    public static final int  Item_image = 1;
    public static final int  Item_content = 2;
    public static final int  Item_parise = 4;
    public static final int  Item_personInfo = 5;
    public static final int  Item_divider = 6;

    private Context mContext;


    private AdapterItemClick adapterItemClick;
    public void setAdapterItemClick(AdapterItemClick adapterItemClick) {
        this.adapterItemClick = adapterItemClick;
    }

    public TweetAdatper(Context context,List<TweentsItemBean> data) {
        super(data);
        mContext = context;
        addItemType(Item_header, R.layout.item_header);
        addItemType(Item_image, R.layout.item_nine_view);
        addItemType(Item_divider, R.layout.item_divider);
        addItemType(Item_content, R.layout.item_content);
        addItemType(Item_parise, R.layout.item_parise);
        addItemType(Item_personInfo, R.layout.item_personinfo);
    }

    @Override
    protected void convert(BaseViewHolder helper, TweentsItemBean item) {
        switch (item.getItemType()){
            case Item_header:
                convertHeader(helper,item);
                break;
            case Item_image:
                convertImages(helper,item);
                break;
            case Item_divider:
                break;
            case Item_content:
                convertContent(helper,item);
                break;
            case Item_parise:
                converParise(helper,item);
                break;
            case Item_personInfo:
                convertPersonInfo(helper,item);
                break;
        }
    }

    private void convertPersonInfo(BaseViewHolder baseViewHolder, TweentsItemBean itemBean){
        ImageView bg = baseViewHolder.getView(R.id.image_bg);
        ImageView avatar = baseViewHolder.getView(R.id.person_avatar);
        TextView name = baseViewHolder.getView(R.id.txt_name);
        name.setText(itemBean.getName());
        bg.setImageResource(R.mipmap.circler_bg);
        Glide.with(mContext).load(itemBean.getAvatar()).into(avatar);


    }

    private void convertHeader(BaseViewHolder baseViewHolder, TweentsItemBean itemBean){
        ImageView avatar = baseViewHolder.getView(R.id.image);
        TextView name = baseViewHolder.getView(R.id.name);
        ExpandTextView content = baseViewHolder.getView(R.id.content);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.discovery_img_2)
                .error(R.mipmap.discovery_img_2);
        Glide.with(mContext).applyDefaultRequestOptions(options).load(itemBean.getAvatar()).into(avatar);
        name.setText(itemBean.getName());
        if (TextUtils.isEmpty(itemBean.getContent())){
            content.setVisibility(View.GONE);
        }else {
            content.setVisibility(View.VISIBLE);
            content.setText(itemBean.getContent());
        }
    }

    private void convertImages(BaseViewHolder baseViewHolder, TweentsItemBean itemBean){
        NineView nineView = baseViewHolder.getView(R.id.nineview);
        nineView.initView(new NineView.NineAdapter() {
            @Override
            public int getSize() {
                return itemBean.getImageList().size();
            }

            @Override
            public View getView() {
                ImageView imageView = new ImageView(mContext);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                /**
                 * 如果是网络图片可以切换成使用glide加载
                 */
                imageView.setImageResource(R.mipmap.discovery_img_2);
                return imageView;
            }
        });
    }

    private void convertContent(BaseViewHolder baseViewHolder, TweentsItemBean itemBean){
        TextView txt = baseViewHolder.getView(R.id.txt_content);
        String name = itemBean.getName();
        String str = itemBean.getName()+":"+itemBean.getContent();

        SpannableStringBuilder spannable = new SpannableStringBuilder(str);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#3700B3")),
                0,
                name.length()+1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")),
                name.length()+1,
                str.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt.setMovementMethod(LinkMovementMethod.getInstance());
        txt.setHighlightColor(Color.TRANSPARENT);
        txt.setText(spannable);
    }

    private void converParise(BaseViewHolder baseViewHolder, TweentsItemBean itemBean){
        ImageView imageView = baseViewHolder.getView(R.id.more);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAttachPopup customAttachPopup = new CustomAttachPopup(mContext, new CustomAttachPopup.Click() {
                    @Override
                    public void onParise() {
                        if (adapterItemClick != null){
                            adapterItemClick.onParise();
                        }
                    }

                    @Override
                    public void onContent() {
                        if (adapterItemClick != null){
                            adapterItemClick.onContent();
                        }
                    }
                });
                new XPopup.Builder(mContext)
                        .isDestroyOnDismiss(true)
                        .hasShadowBg(false)
                        .atView(v)
                        .asCustom(customAttachPopup)
                        .show();
            }
        });
    }

    public interface AdapterItemClick{
        void onParise();
        void onContent();
    }
}
