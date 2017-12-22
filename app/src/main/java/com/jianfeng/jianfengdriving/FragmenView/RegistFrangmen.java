package com.jianfeng.jianfengdriving.FragmenView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.RelativeLayout;

import com.jianfeng.jianfengdriving.MainActivity;
import com.jianfeng.jianfengdriving.Myjava.App;
import com.jianfeng.jianfengdriving.Myjava.CheckUtils;
import com.jianfeng.jianfengdriving.Myjava.EditTextWithDel;
import com.jianfeng.jianfengdriving.Myjava.MyUser;
import com.jianfeng.jianfengdriving.Myjava.Tools;
import com.jianfeng.jianfengdriving.Myjava.VibratorUtil;
import com.jianfeng.jianfengdriving.R;

import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 吴剑锋 on 2017/4/27.
 * 注册
 */

public class RegistFrangmen extends Fragment {
    private Button nextBt, send_smscode;
    private EditTextWithDel userpassword, userphone, smscode;
    private RelativeLayout rela_rephone, rela_recode;
    private RelativeLayout rela_repass;
    private ImageView phoneIv,key_icon;
    private ImageView passIv;
    private MyCountTimer timer;
    private Handler handler = new Handler() {
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragremregist, null);
        inti_view(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        TextListener();
    }

    /**
     * 输入框监听
     */
    private void TextListener() {
        //用户名改变背景变
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
                if (CheckUtils.isMobile(text)) {
                    //抖动
                    rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));

                }

            }
        });
        //验证码改变背景变
        smscode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                rela_recode.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));


            }
        });
        //密码改变背景变
        userpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                rela_repass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));


            }
        });


    }

    /**
     * 按钮点击事件
     */
    private void initView() {
        //发送验证码点击事件
        send_smscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                String phone = userphone.getText().toString();
                boolean mobile = CheckUtils.isMobile(phone);
                if (!TextUtils.isEmpty(phone)) {
                    if (mobile) {
                        timer = new MyCountTimer(60000, 1000);
                        timer.start();
                        BmobSMS.requestSMSCode(phone, "易驾考验证码", new QueryListener<Integer>() {
                            @Override
                            public void done(Integer integer, BmobException e) {
                                if (e == null) {
                                    showSnackar(view, "IYO提示：OK");
                                }
                            }
                        });
                    } else {
                        rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                        phoneIv.setAnimation(Tools.shakeAnimation(2));
                        showSnackar(view, "IYO提示：输入手机号码");
                    }
                } else {
                    rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    phoneIv.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(view, "IYO提示：手机号码不正确");
                }

            }
        });
        //下一步的点击事件
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                nextBt.setEnabled(false);
                final String password = userpassword.getText().toString();
                String code = smscode.getText().toString();
                final String phone = userphone.getText().toString();

                if (TextUtils.isEmpty(phone)) {
//                    rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    rela_rephone.setBackgroundResource(R.drawable.bg_border_color_cutmaincolor);
                    phoneIv.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(view, "IYO提示：请输入手机号码");
                    VibratorUtil.Vibrate(getActivity(), 200);
                    return;
                }
                if (!CheckUtils.isMobile(phone)) {
//                    rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    rela_rephone.setBackgroundResource(R.drawable.bg_border_color_cutmaincolor);
                    phoneIv.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(view, "IYO提示：手机号不正确");
                    VibratorUtil.Vibrate(getActivity(), 200);
                    return;
                }
                if (TextUtils.isEmpty(code)){
//                    rela_recode.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    rela_recode.setBackgroundResource(R.drawable.bg_border_color_cutmaincolor);
                    key_icon.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(view,"IYO提示：请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
//                    rela_repass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    rela_repass.setBackgroundResource(R.drawable.bg_border_color_cutmaincolor);
                    passIv.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(view, "IYO提示：请输入密码");
                    VibratorUtil.Vibrate(getActivity(), 200);
                    return;
                }
                final MyUser user = new MyUser();
                user.setPassword(password);
                user.setMobilePhoneNumber(phone);
                user.setUsername(phone);
                user.setMy_icon("http://file.bmob.cn/M03/28/FE/oYYBAFcNtReAc4WkAACceyr8Oc8966.png");
                user.setMy_name(phone);
                //验证验证码是否正确
                 BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
                     @Override
                     public void done(BmobException e) {
                         if(e==null){
                             user.signUp(new SaveListener<MyUser>() {
                                 @Override
                                 public void done(MyUser myUser, BmobException e) {
                                     if (e == null) {
                                         Tools.saveUserInfo(getActivity(), "userinfo", phone, password);
                                         App app = (App) getActivity().getApplication();
                                         app.myUser = myUser;
                                         handler.postDelayed(new Runnable() {
                                             @Override
                                             public void run() {
                                                 Intent intent = new Intent(getActivity(), MainActivity.class);
                                                 startActivity(intent);
                                                 getActivity().overridePendingTransition(R.anim.fade,
                                                         R.anim.my_alpha_action);
                                                 getActivity().finish();
                                             }
                                         }, 1000);
                                         showSnackar(view, "IYO提示：注册成功");
                                         VibratorUtil.Vibrate(getActivity(), 200);
                                     } else {
                                         showSnackar(view, "IYO提示：注册失败");
                                         nextBt.setEnabled(true);
                                         VibratorUtil.Vibrate(getActivity(), 200);
                                     }
                                 }
                             });
                         }
                         else {
//                             rela_recode.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                             rela_recode.setBackgroundResource(R.drawable.bg_border_color_cutmaincolor);
                             key_icon.setAnimation(Tools.shakeAnimation(2));
                             // fg_regist.setBackgroundResource(R.color.colorAccent);
                             nextBt.setEnabled(true);
                             showSnackar(view,"IYO提示：验证码错误");
                         }
                     }
                 });

            }
        });
    }
    private void inti_view(View v) {
        nextBt = (Button) v.findViewById(R.id.next);
        send_smscode = (Button) v.findViewById(R.id.send_smscode);
        userpassword = (EditTextWithDel) v.findViewById(R.id.userpassword);
        userphone = (EditTextWithDel) v.findViewById(R.id.userphone);
        smscode = (EditTextWithDel) v.findViewById(R.id.smscode);
        rela_rephone = (RelativeLayout) v.findViewById(R.id.rela_rephone);
        rela_repass = (RelativeLayout) v.findViewById(R.id.rela_repass);
        rela_recode = (RelativeLayout) v.findViewById(R.id.rela_recode);
        phoneIv = (ImageView) v.findViewById(R.id.usericon);
        passIv = (ImageView) v.findViewById(R.id.codeicon);
        key_icon = (ImageView) v.findViewById(R.id.key_icon);
    }

    private void showSnackar(View view, String meg) {
        Snackbar.make(view, meg, Snackbar.LENGTH_LONG)
                .show();
    }
    //事件定时器
    class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            send_smscode.setText((millisUntilFinished / 1000) + "秒后重发");
            send_smscode.setClickable(false);
        }
        @Override
        public void onFinish() {
            send_smscode.setText("重新发送");
            send_smscode.setClickable(true);
        }
    }

    //回收timer
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
