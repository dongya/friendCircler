package com.dongya.friendcircle.http;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * FileName: HttpManager
 * Author: dongya
 * Date: 2021/2/7 11:09 AM
 * Description:
 */
public class HttpManager {

    private static HttpManager httpManager;
    private static Retrofit retrofit;
    public static HttpManager getInstance(){
        if (httpManager == null){
            synchronized (HttpManager.class){
                if (httpManager == null){
                    return new HttpManager();
                }
            }
        }
        return httpManager;
    }


    private HttpManager(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(5*60, TimeUnit.SECONDS);
        builder.readTimeout(5*60, TimeUnit.SECONDS);

        LoggingInterceptor logging = new LoggingInterceptor(new LoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("dongya",message);
            }
        });
        logging.setLevel(LoggingInterceptor.Level.BODY);
        builder.addNetworkInterceptor(logging);


        /**
         * 如有特需输球可自定拦截器，如添加请求头活着cookie等
         */
//        Interceptor interceptor = new Interceptor() {
//            @NotNull
//            @Override
//            public Response intercept(@NotNull Chain chain) throws IOException {
//                return null;
//            }
//        };
//        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://thoughtworks-ios.herokuapp.com")
                .build();
    }

    public static <T> T getHttpService(Class<T> t){
        return retrofit.create(t);
    }

}
