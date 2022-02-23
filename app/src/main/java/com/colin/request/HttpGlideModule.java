package com.colin.request;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Date:2022-01-12
 * Time:10:02
 * author:colin
 * 用来拦截登陆时候的验证码用的
 */
@GlideModule
public class HttpGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);

        // 请求拦截器
        RequestInterceptor requestInterceptor = new RequestInterceptor();
        OkHttpClient mClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build();

        // 注意这里用我们刚才现有的Client实例传入即可
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(mClient));

    }
}
