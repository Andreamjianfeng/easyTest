package com.jianfeng.jianfengdriving;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Picture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianfeng.jianfengdriving.Myjava.ActivityCollector;
import com.jianfeng.jianfengdriving.Myjava.App;
import com.jianfeng.jianfengdriving.Myjava.CircleTransform;
import com.jianfeng.jianfengdriving.Myjava.MyUser;
import com.jianfeng.jianfengdriving.Myjava.Tools;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PeopleActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView people_back, my_icon_img;
    private TextView my_name_tv;
    private RelativeLayout people_about_me, people_friend, people_set_me, people_album_me, people_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        inti_view();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inti_data();
    }

    /**
     * 初始化控件
     */
    private void inti_view() {
        people_back = (ImageView) findViewById(R.id.people_back);
        my_icon_img = (ImageView) findViewById(R.id.my_icon_img);
        my_name_tv = (TextView) findViewById(R.id.my_name_tv);
        people_about_me = (RelativeLayout) findViewById(R.id.people_about_me);
        people_friend = (RelativeLayout) findViewById(R.id.people_friend);
        people_set_me = (RelativeLayout) findViewById(R.id.people_set_me);
        people_album_me = (RelativeLayout) findViewById(R.id.people_album_me);
        people_go = (RelativeLayout) findViewById(R.id.people_go);

        people_back.setOnClickListener(this);
        people_about_me.setOnClickListener(this);
        people_friend.setOnClickListener(this);
        people_set_me.setOnClickListener(this);
        people_album_me.setOnClickListener(this);
        people_go.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void inti_data() {
        App app = (App)getApplication();
        my_name_tv.setText(app.myUser.getMy_name());
        Picasso.with(this).load(app.myUser.getMy_icon()).into(my_icon_img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.people_back:
                finish();
                break;
            case R.id.people_about_me:
                startActivity(new Intent(this,SettingActivity.class));
                break;
            case R.id.people_friend:
                startActivity(new Intent(this,FriendActivity.class));
                break;
            case R.id.people_set_me:
                break;
            case R.id.people_album_me:
                break;
            case R.id.people_go:
                ActivityCollector.finishAll();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
}
