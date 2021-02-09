package com.dongya.friendcircle.popwindow;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dongya.friendcircle.R;
import com.lxj.xpopup.core.HorizontalAttachPopupView;

/**
 * FileName: CustomAttachPopup
 * Author: dongya
 * Date: 2021/2/9 9:44 AM
 * Description:
 */
public class CustomAttachPopup extends HorizontalAttachPopupView {

    private Click click;
    public CustomAttachPopup(@NonNull Context context,Click click) {
        super(context);
        this.click = click;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_attach_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_zan).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                click.onParise();
            }
        });
        findViewById(R.id.tv_comment).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                click.onContent();
            }
        });
    }

    public interface Click{
        void onParise();
        void onContent();
    }
}
