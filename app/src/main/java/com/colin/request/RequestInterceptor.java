package com.colin.request;

import androidx.annotation.NonNull;


import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Date:2022-01-12
 * Time:15:32
 * author:colin
 * 请求拦截器，拦截登陆时候验证码用的
 */
public class RequestInterceptor implements Interceptor {

    public static String sobCaptchaKey = "";
    private static final String SOB_CAPTCHA_KEY_NAME = "l_c_i";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        Response response = chain.proceed(builder.build());
        Headers responseHeaders = response.headers();

        String url = request.url().toString();

        if (url.contains("uc/ut/captcha")) {
            sobCaptchaKey = responseHeaders.get(SOB_CAPTCHA_KEY_NAME);
//            Log.e("sobCaptchaKey" , sobCaptchaKey);
        }

        return response;
    }
}
