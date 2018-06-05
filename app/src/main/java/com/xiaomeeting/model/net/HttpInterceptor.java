package com.xiaomeeting.model.net;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Blinger on 2018/6/1.
 */

public class HttpInterceptor implements Interceptor {
    private Context context;

    public HttpInterceptor(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        String cookie = sharedPreferences.getString("cookie", "");
        Request request = chain.request();
        Response response;
        if (!cookie.equals("")) {
            Request compressedRequest = request.newBuilder()
                    .header("Content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("cookie", cookie.substring(0, cookie.length() - 1))
                    .build();

            response = chain.proceed(compressedRequest);
        } else {
            response = chain.proceed(request);
        }
        return response;
    }
}
