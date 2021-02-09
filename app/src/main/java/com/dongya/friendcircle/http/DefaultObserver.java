package com.dongya.friendcircle.http;

public interface DefaultObserver<T> {
    /**
     * 失败
     */
    void onFail(int code, String msg);

    /**
     * 成功
     */
    void onSuccess(T t);
}
