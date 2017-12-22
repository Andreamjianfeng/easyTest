package com.jianfeng.jianfengdriving;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jianfeng.jianfengdriving.MyAdapter.FriendAdapter;
import com.jianfeng.jianfengdriving.Myjava.App;
import com.jianfeng.jianfengdriving.Myjava.FriendSay;
import com.jianfeng.jianfengdriving.Myjava.GradationScrollView;
import com.jianfeng.jianfengdriving.Myjava.NoScrollListview;
import com.jianfeng.jianfengdriving.Myjava.Tools;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FriendActivity extends AppCompatActivity implements View.OnClickListener, GradationScrollView.ScrollViewListener {
    private ImageView friend_back_img, iv_banner, friend_add_img;
    private NoScrollListview friend_list;
    private RelativeLayout friend_change_re;
    private GradationScrollView scrollView;
    private int height;
    private List<FriendSay> orders;
    private RoundedImageView my_friend_icon;
    private FriendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        inti_view();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inti_data();
    }

    /**
     * 初始化view
     */
    private void inti_view() {
        friend_add_img = (ImageView) findViewById(R.id.friend_add_img);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        friend_back_img = (ImageView) findViewById(R.id.friend_back_img);
        friend_list = (NoScrollListview) findViewById(R.id.friend_list);
        friend_change_re = (RelativeLayout) findViewById(R.id.friend_change_re);
        scrollView = (GradationScrollView) findViewById(R.id.scrollview);
        my_friend_icon = (RoundedImageView) findViewById(R.id.my_friend_icon);
        friend_add_img.setOnClickListener(this);
        friend_back_img.setOnClickListener(this);
        iv_banner.setFocusable(true);
        iv_banner.setFocusableInTouchMode(true);
        iv_banner.requestFocus();
        initListeners();
        App app=(App) getApplication();
        Picasso.with(this).load(app.myUser.getMy_icon()).into(my_friend_icon);
        adapter=new FriendAdapter(this,orders,app.myUser.getUsername());
        friend_list.setAdapter(adapter);
        adapter.setadapterDatachange(new FriendAdapter.AdapterDatachange() {
            @Override
            public void change() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 查询数据
     */
    private void inti_data() {
        BmobQuery<FriendSay> query =new BmobQuery<>();
        query.order("-createdAt");
        query.include("myUser");
        query.findObjects(new FindListener<FriendSay>() {
            @Override
            public void done(List<FriendSay> list, BmobException e) {
                if(e==null){
                    orders=list;
                    adapter.add_order(orders);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friend_add_img:
                startActivity(new Intent(this, PublishedActivity.class));
                break;
            case R.id.friend_back_img:
                finish();
                break;
        }
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {

        ViewTreeObserver vto = iv_banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                friend_change_re.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = iv_banner.getHeight();

                scrollView.setScrollViewListener(FriendActivity.this);
            }
        });
    }

    /**
     * 滑动监听
     *
     */
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {   //设置标题的背景颜色
            friend_change_re.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
        } else if (y > 0 && y <= height-10) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            friend_change_re.setBackgroundColor(Color.argb((int) alpha, 55, 147, 199));
            friend_change_re.setBackgroundColor(Color.argb((int) alpha, 55, 147, 199));
        } else {    //滑动到banner下面设置普通颜色
            friend_change_re.setBackgroundColor(Color.argb((int) 255, 55, 147, 199));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
