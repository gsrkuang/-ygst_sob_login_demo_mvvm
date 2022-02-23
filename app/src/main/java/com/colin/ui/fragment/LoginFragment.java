package com.colin.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.colin.ui.activity.MainActivity;
import com.colin.ygst.R;

/**
 * Date:2022-02-18
 * Time:15:26
 * author:colin
 */
public class LoginFragment extends Fragment {

    final public static String epUsers="体验用户";

    private LoginAccountFragment loginAccountFragment =new LoginAccountFragment();
    //两个选择1.跳转至输入手机号页面
    //   2.跳转至主页面
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //该View表示该碎片的主界面,最后要返回该view
        View view = inflater.inflate(R.layout.login_fragment,container,false);


        //bt01立即登录的点击事件
        view.findViewById(R.id.dl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                LoginAccountFragment loginAccountFragment = (LoginAccountFragment) getActivity().getSupportFragmentManager().findFragmentByTag("loginAccountFragment");
//                LoginFragment loginFragment = (LoginFragment) getActivity().getSupportFragmentManager().findFragmentByTag("loginFragment");
//
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .addToBackStack("loginFragment")
//                        .show(loginAccountFragment)
//                        .hide(loginFragment)
//                        .commit();

//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .addToBackStack(null)
//
//                        .commit();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)//将当前fragment加入到返回栈中
                        //通过FragmentTransaction对象的replace()方法让OtherFragment
                        // 把当前Fragment替换成输入手机号的Fragment，
                        .replace(R.id.fl_denglu,loginAccountFragment)
                        .commit();
            }
        });

        //bt02 点击体验
        Button bt02 = view.findViewById(R.id.ty);
        bt02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editor.putString("experienceUsers",epUsers);
//                editor.commit();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }).start();

                //从DengluActivity切换到MianShiActivity 直接跳
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);

            }
        });

        view.findViewById(R.id.btn_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"还没有开放~",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
