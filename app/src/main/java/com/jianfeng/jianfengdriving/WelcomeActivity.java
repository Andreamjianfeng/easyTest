package com.jianfeng.jianfengdriving;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.jianfeng.jianfengdriving.Myjava.App;
import com.jianfeng.jianfengdriving.Myjava.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class WelcomeActivity extends AppCompatActivity {
    private  Intent intent;
    private boolean is_OK=false;
    private Handler handler = new Handler() {
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "f7e7f5c2bafc7922d3848fe1234d7c8a");
        setContentView(R.layout.activity_welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        welcome();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(is_OK){
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                }
                else {
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                WelcomeActivity.this.overridePendingTransition(R.anim.fade,
                        R.anim.my_alpha_action);
                WelcomeActivity.this.finish();
            }
        }, 2000);
    }
    private void welcome(){
        SharedPreferences userinfo = getApplication().getSharedPreferences("userinfo", 0);
        String username=userinfo.getString("username", null);
        String password=userinfo.getString("password", null);
        final MyUser user = new MyUser();
        user.setPassword(password);
        user.setUsername(username);
        user.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    is_OK=true;
                    App app = (App)getApplication();
                    app.myUser=myUser;
                }
                else {
                   is_OK=false;
                }

            }
        });
    }
}
