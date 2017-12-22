package com.jianfeng.jianfengdriving.FragmenView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianfeng.jianfengdriving.MainActivity;
import com.jianfeng.jianfengdriving.Myjava.App;
import com.jianfeng.jianfengdriving.Myjava.CheckUtils;
import com.jianfeng.jianfengdriving.Myjava.EditTextWithDel;
import com.jianfeng.jianfengdriving.Myjava.MyUser;
import com.jianfeng.jianfengdriving.Myjava.Tools;
import com.jianfeng.jianfengdriving.Myjava.VibratorUtil;
import com.jianfeng.jianfengdriving.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 吴剑锋 on 2017/4/27.
 * 登录Fragment
 */

public class LoginFragmen extends Fragment {
    private RelativeLayout rela_name, rela_pass;
    private ImageView loginusericon, codeicon;
    private EditTextWithDel userpass, userphone;
    private Button bt_login;
    private ProgressBar login_progress;
    private TextView tv_forgetcode;
    private Handler handler = new Handler() {
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragramlogin, null);
        inti_view(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLogin();
        textListener();
    }
    private void textListener() {
        userphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = userphone.getText().toString();
                if(CheckUtils.isMobile(text)){
                    //抖动
                    rela_name.setBackgroundResource(R.drawable.bg_border_color_black);

                }

            }
        });
        userpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                rela_pass.setBackgroundResource(R.drawable.bg_border_color_black);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //rela_pass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));

            }
        });
    }

    private void inti_view(View v) {
        rela_name = (RelativeLayout) v.findViewById(R.id.rela_name);
        rela_pass = (RelativeLayout) v.findViewById(R.id.rela_pass);
        loginusericon = (ImageView) v.findViewById(R.id.loginusericon);
        codeicon = (ImageView) v.findViewById(R.id.codeicon);
        bt_login = (Button) v.findViewById(R.id.bt_login);
        login_progress = (ProgressBar) v.findViewById(R.id.login_progress);
        tv_forgetcode = (TextView) v.findViewById(R.id.tv_forgetcode);
        userpass = (EditTextWithDel) v.findViewById(R.id.userpass);
        userphone = (EditTextWithDel) v.findViewById(R.id.userph);
    }

    private void initLogin() {
        SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo", 0);
        userphone.setText(userinfo.getString("username", null));
        userpass.setText(userinfo.getString("password", null));
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_login.setEnabled(false);
                final String phone = userphone.getText().toString();
                final String passwords = userpass.getText().toString();
                final View view = v;
                if (TextUtils.isEmpty(phone)) {
//                    rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    rela_name.setBackgroundResource(R.drawable.bg_border_color_cutmaincolor);
                    loginusericon.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(v,"IYO提示：请输入手机号码");
                    VibratorUtil.Vibrate(getActivity(),200);
                    bt_login.setEnabled(true);
                    return;
                }
                if (!CheckUtils.isMobile(phone)) {
                    //抖动
//                    rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    rela_name.setBackgroundResource(R.drawable.bg_border_color_cutmaincolor);
                    loginusericon.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(v,"IYO提示：用户名不正确");
                    VibratorUtil.Vibrate(getActivity(),200);
                    bt_login.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(passwords)) {
//                    rela_pass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    rela_pass.setBackgroundResource(R.drawable.bg_border_color_cutmaincolor);
                    codeicon.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(v,"IYO提示：请输入密码");
                    VibratorUtil.Vibrate(getActivity(),200);
                    bt_login.setEnabled(true);
                    return;
                }
                login_progress.setVisibility(View.VISIBLE);
                final MyUser user = new MyUser();
                user.setPassword(passwords);
                user.setUsername(phone);
                user.setMobilePhoneNumber(phone);
                user.login(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if(e==null){
//                            rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));
                            rela_name.setBackgroundResource(R.drawable.bg_border_color_black);
                            Tools.saveUserInfo(getActivity(),"userinfo",phone,passwords);
                            showSnackar(view,"IYO提示：登陆成功");
                            //((App)getActivity().getApplication()).myUser=myUser;
                            App app = (App) getActivity().getApplication();
                            app.myUser=myUser;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    login_progress.setVisibility(View.GONE);
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.fade,
                                            R.anim.my_alpha_action);
                                    getActivity().finish();
                                }
                            },1000);
                        } else{
                            login_progress.setVisibility(View.GONE);
//                            rela_pass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                            rela_pass.setBackgroundResource(R.drawable.bg_border_color_black);
                            codeicon.setAnimation(Tools.shakeAnimation(2));
                            showSnackar(view,"IYO提示：登陆失败");
                            bt_login.setEnabled(true);
                            VibratorUtil.Vibrate(getActivity(),200);
                        }
                    }
                });
            }
        });

    }
    private void showSnackar(View view,String meg){
        Snackbar.make(view, meg, Snackbar.LENGTH_LONG)
                .show();
    }
}
