package com.dongya.friendcircle.http;

import android.content.Context;

import com.dongya.friendcircle.bean.TweetsBean;
import com.dongya.friendcircle.bean.UserInfoBean;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * FileName: HttpApi
 * Author: dongya
 * Date: 2021/2/7 1:59 PM
 * Description:
 */
public class HttpApi {




    private static <T> void request(Observable<T> observable, DefaultObserver<T> retrofitInterfece) {
        if (observable != null) {
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultSubscriber<T>(retrofitInterfece));
        }
    }

    public static HttpInterface HttpInterface = HttpManager.getHttpService(HttpInterface.class);

    /**
     * 获取用户信息
     * @param defaultObserver
     */
    public static void getUserInfo(DefaultObserver<UserInfoBean> defaultObserver){
        request(HttpInterface.getUserInfo(),defaultObserver);
    }

    /**
     * 获取朋友圈
     * @param defaultObserver
     */
    public static void getTweets(DefaultObserver<List<TweetsBean>> defaultObserver){
        request(HttpInterface.getTweets(),defaultObserver);
    }

}
