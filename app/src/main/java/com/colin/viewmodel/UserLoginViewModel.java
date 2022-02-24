package com.colin.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.colin.bean.LoginBean;
import com.colin.bean.UserAvaterBean;
import com.colin.bean.UserBean;
import com.colin.model.ServerApi;
import com.colin.request.RequestInterceptor;
import com.colin.ui.activity.MainActivity;
import com.colin.util.Constants;
import com.colin.util.MD5Util;
import com.colin.ygst.R;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date:2022-02-22
 * Time:15:12
 * author:colin
 * 更改为MVVM开发架构
 */
public class UserLoginViewModel {

    private Activity mContext;
    private static final String TAG = "UserLoginViewModel";

    public ObservableField<String> imgUrl=new ObservableField<>();
    public ObservableField<String> edit_account=new ObservableField<>();
    public ObservableField<String> edit_password=new ObservableField<>();
    public ObservableField<String> edit_captcha=new ObservableField<>();

    public ObservableField<String> img_Captcha_url=new ObservableField<>();

    public UserLoginViewModel(Activity mContext){
        this.mContext = mContext;
    }




    @BindingAdapter({"setCaptcha"})
    public static void loadCaptcha(ImageView imageView,String url){

        Glide.with(imageView.getContext())
                .load(String.format(Constants.api_main + "uc/ut/captcha?code=%s", System.currentTimeMillis()))
                .placeholder( R.drawable.icon_ygst) //加载成功前显示的图片
                .fallback( R.drawable.icon_ygst) //url为空的时候,显示的图片
                .into(imageView);
    }


    public void refreshCaptcha(View view){
        AppCompatImageView imageView = (AppCompatImageView) view;
        Glide.with(view.getContext())
                .load(String.format(Constants.api_main + "uc/ut/captcha?code=%s", System.currentTimeMillis()))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }


    //用户输入账号时候进行监听
    public TextWatcher accChange(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (start == 10){
                    getUserAvater(s+"");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }



    public void getUserAvater(String acoount){
        //自动设置用户头像
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.api_main) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create())//设置数据解析器
                .build();

        ServerApi serverApi = retrofit.create(ServerApi.class);

        Call<UserAvaterBean> call = serverApi.setUserAvater(acoount);

        call.enqueue(new Callback<UserAvaterBean>() {
            @Override
            public void onResponse(Call<UserAvaterBean> call, Response<UserAvaterBean> response) {
                UserAvaterBean userAvaterBean = response.body();
                String url = userAvaterBean.getData();
                imgUrl.set(url);
            }

            @Override
            public void onFailure(Call<UserAvaterBean> call, Throwable throwable) {

            }
        });
    }


    public void login(View view) {

//        Log.e("++++test", userLoginView.getUserName());

        String acc = edit_account.get();
        String pass = edit_password.get();
        String captcha = edit_captcha.get();

        if (acc == null || pass == null || captcha == null){
            return;
        }

        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.api_main) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create())//设置数据解析器
                .build();

        ServerApi serverApi = retrofit.create(ServerApi.class);
        UserBean userBean = new UserBean();
        userBean.setPhoneNum(acc);

        try {
            //这里要加密accChange
            userBean.setPassword(MD5Util.getMD5String(pass));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

//        Log.e("+sobCaptchaKey" , RequestInterceptor.sobCaptchaKey);

        Call<LoginBean> call = serverApi.userLogin(RequestInterceptor.sobCaptchaKey, userBean, captcha);

        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                //Response<LoginBean> responseBody  = call.execute();

                Log.e("+++login", response.headers().toString());

                //登陆成功后，获取sob_token
                String sob_token = response.headers().get("sob_token");

//                Log.e("+++login++sob_token" , sob_token );
//                response.headers().

                LoginBean loginBean = response.body();
                if (loginBean != null || "".equals(loginBean)) {
                    showTips(loginBean);
                }
                if (loginBean.isSuccess()) {
                    //从DengluActivity切换到MianShiActivity 直接跳
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    mContext.finish();
                }

            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable throwable) {

            }
        });

    }

    public void showTips(LoginBean loginBean) {
        Log.e("+++login", loginBean.toString());
        Toast.makeText(mContext, loginBean.getMessage(), Toast.LENGTH_SHORT).show();

    }

    public void ToMainActivity(View view){
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);
        mContext.finish();
    }

}

