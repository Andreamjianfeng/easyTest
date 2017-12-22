package com.jianfeng.jianfengdriving;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.jianfeng.jianfengdriving.FragmenView.GuildFragment;
import com.jianfeng.jianfengdriving.FragmenView.LoginFragmen;
import com.jianfeng.jianfengdriving.FragmenView.RegistFrangmen;
import cn.bmob.v3.Bmob;
public class LoginActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioGroup rg;
    private View vi;
    private ViewPager vp,video_vp;
    private FragmentPagerAdapter m_adpter;
    private List<Fragment> list_frangmen = new ArrayList<>();
    private int width = 250,left_width=0;
    private List<Fragment> fragments;
    private static final String TAG="OK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "f7e7f5c2bafc7922d3848fe1234d7c8a");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        InitTextView();
        initData();
        initView();
    }

    /**
     * 初始化头标
     */
    private void InitTextView() {
        rg = (RadioGroup) findViewById(R.id.home_rg);
        vp = (ViewPager) findViewById(R.id.home_vp);
        video_vp = (ViewPager) findViewById(R.id.video_vp);
        vi = findViewById(R.id.home_fl_view);
        //创建适配器,设置的碎片管理器使用的是getChildFragmentManager()
        //给ViewPager设置适配器
        list_frangmen.add(new LoginFragmen());
        list_frangmen.add(new RegistFrangmen());
        m_adpter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list_frangmen.get(position);
            }
            @Override
            public int getCount() {
                return list_frangmen.size();
            }
        };
        vp.setAdapter(m_adpter);
        //给控件设置监听事件
        rg.setOnCheckedChangeListener(this);
        //给ViewPager设置监听器使用的是add而不是set了
        vp.addOnPageChangeListener(this);
        initVi();

    }
    /**
     * 屏幕一般的宽度
     * 把下划线View设置初始值
     */
    private void initVi() {
        //设置下划线View的长度
        int window_width=getResources().getDisplayMetrics().widthPixels/2;
        width = window_width / 5*3;
        left_width = window_width / 5*2;
        FrameLayout.LayoutParams par = new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        par.setMargins(left_width,0,0,0);
        vi.setLayoutParams(par);

    }

    /**
     * 初始化背景小视频Fragment
     */
    private void initData() {
        fragments = new ArrayList<>();
        Fragment fragment1 = new GuildFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("index", 1);
        fragment1.setArguments(bundle1);
        fragments.add(fragment1);
    }
    /**
     * 设置ViewPager的适配器和滑动监听
     */
    private void initView() {
        video_vp.setOffscreenPageLimit(1); //原为3
        video_vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//"页面:" + position + "
        // offset偏移百分比" + positionOffset
        // pix像素" + positionOffsetPixels
        //设置下划线的属性
        //设置下划线View的长度
        FrameLayout.LayoutParams par = (FrameLayout.LayoutParams) vi.getLayoutParams();
        //设置下划线距离左边的位置长度
        int left = (int) ((positionOffset + position) * width)+left_width;
        par.setMargins(left, 0, 0, 0);
        vi.setLayoutParams(par);
    }

    @Override
    public void onPageSelected(int position) {
        rg.check(position == 0 ? R.id.home_rb_dujia : R.id.home_rb_ticker);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        vp.setCurrentItem(i == R.id.home_rb_dujia ? 0 : 1);
    }

    /**
     * viewpager适配器
     */
    private class MyPageAdapter extends FragmentPagerAdapter {


        public  MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
