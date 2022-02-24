package com.colin.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.colin.ui.activity.MainActivity;
import com.colin.viewmodel.UserLoginViewModel;
import com.colin.ygst.R;
import com.colin.ygst.databinding.AccountLoginFragmentBinding;

/**
 * Date:2022-02-18
 * Time:15:06
 * author:colin
 * 用户登录界面
 */
public class LoginAccountFragment extends Fragment {

    // DataBinding
    private AccountLoginFragmentBinding mBinding;

    private UserLoginViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.account_login_fragment, container, false);

        viewModel = new UserLoginViewModel(getActivity());

        mBinding.setUserLoginViewModel(viewModel);

        View view = mBinding.getRoot();//inflater.inflate(R.layout.test_fragment_idcard, container, false);

        initEvent(view);

        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    private void initEvent(View view) {


        view.findViewById(R.id.title_bar_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从DengluActivity切换到MianShiActivity 直接跳
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        view.findViewById(R.id.title_bar_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "请到官网注册哦~", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
