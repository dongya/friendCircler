package com.dongya.friendcircle;

import android.app.Application;
import android.content.Context;

import com.dongya.friendcircle.http.HttpManager;

/**
 * FileName: App
 * Author: dongya
 * Date: 2021/2/7 2:17 PM
 * Description:
 */
public class App extends Application {

    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HttpManager.getInstance();
        context = this;
    }
}
