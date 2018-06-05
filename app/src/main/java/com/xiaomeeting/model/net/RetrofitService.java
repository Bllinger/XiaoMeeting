package com.xiaomeeting.model.net;

import com.xiaomeeting.app.MyApplication;
import com.xiaomeeting.constants.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Blinger on 2018/5/5.
 */

public class RetrofitService {

    private static RetrofitService Instance = null;

    public static RetrofitService getInstance(){
        if (Instance == null){
            Instance = new RetrofitService();
        }

        return Instance;
    }

    private String Tag = RetrofitService.class.getSimpleName();
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    private RetrofitService(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new GetCookiesInterceptor(MyApplication.getContext()))
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public <T> T createApi(Class<T> tClass){
        return retrofit.create(tClass);
    }


}
