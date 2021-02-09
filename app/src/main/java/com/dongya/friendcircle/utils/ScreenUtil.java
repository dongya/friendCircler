package com.dongya.friendcircle.utils;

import android.content.Context;
import android.view.View;

import com.dongya.friendcircle.App;

import java.lang.reflect.Field;

/**
 * 获取屏幕,分辨率相关
 */
public class ScreenUtil {


    /**
     * 把密度转换为像素
     */
    public static int dip2px(int dip) {
        float density = getDensity(App.getContext());
        return (int) (dip * density + 0.5);
    }

    public static int px2dip(int px) {
        float density = getDensity(App.getContext());
        return (int) ((px - 0.5) / density);
    }

    private static float getDensity(Context ctx) {
        return ctx.getResources().getDisplayMetrics().density;
    }

    //获得设备的宽度
    public static int getScreenWidth() {
        return App.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    //得到设备的高度
    public static int getScreenHeight() {
        return App.getContext().getResources().getDisplayMetrics().heightPixels;
    }

}