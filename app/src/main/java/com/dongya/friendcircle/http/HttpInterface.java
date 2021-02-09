package com.dongya.friendcircle.http;

import com.dongya.friendcircle.bean.TweetsBean;
import com.dongya.friendcircle.bean.UserInfoBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

/**
 * FileName: HttpInterface
 * Author: dongya
 * Date: 2021/2/7 2:10 PM
 * Description:
 */
public interface HttpInterface {

    @GET(HttpUrl.user_info)
    Observable<UserInfoBean> getUserInfo();

    @GET(HttpUrl.tweets)
    Observable<List<TweetsBean>> getTweets();
}
