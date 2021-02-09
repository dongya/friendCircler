package com.dongya.friendcircle.http;

import io.reactivex.rxjava3.observers.DisposableObserver;

/**
 *
 * @param <T>
 */
public class DefaultSubscriber<T> extends DisposableObserver<T> {

    private DefaultObserver<T> retrofitInterfece;

    public DefaultSubscriber(DefaultObserver<T> retrofitInterfece) {
        super();
        this.retrofitInterfece = retrofitInterfece;
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        int errorCode = -1;
        if (retrofitInterfece != null) {
            retrofitInterfece.onFail(errorCode, e.getMessage());
        }

    }

    @Override
    public void onNext(T t) {
        if (retrofitInterfece == null) {
            return;
        }
        retrofitInterfece.onSuccess(t);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
