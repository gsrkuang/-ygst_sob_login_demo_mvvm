package com.colin.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.colin.ui.activity.MainActivity;
import com.colin.util.Constants;
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

    private Button mLogin;
    private EditText mVerificationCode;
    private EditText mUserName;
    private EditText mPassWord;
    private AppCompatImageView mVerificationImg;
    private ImageView dl_user_avater;

    private TextView title_bar_jump ;
    private TextView title_bar_register ;
//    UserLoginPresenter userLoginPresenter;


    // DataBinding
    private AccountLoginFragmentBinding mBinding;

    private UserLoginViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding =  DataBindingUtil.inflate(inflater, R.layout.account_login_fragment,container,false);

        viewModel = new UserLoginViewModel(getActivity());

        mBinding.setUserLoginViewModel(viewModel);

        View view = mBinding.getRoot();//inflater.inflate(R.layout.test_fragment_idcard, container, false);

        initView(view);


//        return view;

        //该View表示该碎片的主界面,最后要返回该view
//        View view = inflater.inflate(R.layout.account_login_fragment,container,false);
//        initView(view);

//        initEvent();

//        refreshCaptcha();

//        userLoginPresenter = new UserLoginPresenter(getActivity(), this);

        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    private void initEvent(){

//        mVerificationImg.setOnClickListener(view -> refreshCaptcha());

//        mLogin.setOnClickListener(view -> {
//
//            userLoginPresenter.login();
//
//        });

        title_bar_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从DengluActivity切换到MianShiActivity 直接跳
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        title_bar_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"请到官网注册哦~",Toast.LENGTH_SHORT).show();
            }
        });

        //动态监听用户账号输入
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i == 10){
//                    userLoginPresenter.setUserAvater(charSequence.toString(),dl_user_avater);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void initView(View view) {
        mLogin = view.findViewById(R.id.dl_btn);
        mVerificationCode = view.findViewById(R.id.dl_verificationCode);
        mPassWord = view.findViewById(R.id.dl_password);
        mUserName = view.findViewById(R.id.dl_account);
        mVerificationImg = view.findViewById(R.id.dl_img_verify_code);
        title_bar_jump = view.findViewById(R.id.title_bar_jump);
        title_bar_register = view.findViewById(R.id.title_bar_register);
        dl_user_avater = view.findViewById(R.id.dl_user_avater);



    }






}
