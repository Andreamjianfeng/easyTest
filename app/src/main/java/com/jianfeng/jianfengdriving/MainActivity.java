package com.jianfeng.jianfengdriving;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.jianfeng.jianfengdriving.MyAdapter.ViewPagerAdapter;
import com.jianfeng.jianfengdriving.MyAdapter.MyAdpapter;
import com.jianfeng.jianfengdriving.Myjava.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView one, two, three, five, Two_subjects1, Two_subjects2, Two_subjects3, Two_subjects4, Two_subjects5;
    private Button starttext1, startfive, testrecord_one, gotest_one, testrecord_five, gotest_five;
    private Boolean have_wife = false;
    private final Intent intent = new Intent();
    private final Bundle b = new Bundle();
    private boolean backFlag = false;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private String three_subject[] = {"http://oekt4jwrq.bkt.clouddn.com/%E4%B8%8A%E8%BD%A6%E5%87%86%E5%A4%87.mp4", "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E5%8F%98%E6%9B%B4%E8%BD%A6%E9%81%93.mp4",
            "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E4%BC%9A%E8%BD%A6.mp4",
            "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E5%8A%A0%E5%87%8F%E6%A1%A3%E6%93%8D%E4%BD%9C.mp4", "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E8%B6%85%E8%BD%A6.mp4",
            "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E6%8E%89%E5%A4%B4.mp4", "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E9%80%9A%E8%BF%87%E5%AD%A6%E6%A0%A1.mp4",
            "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E9%80%9A%E8%BF%87%E5%85%AC%E4%BA%A4%E8%BD%A6%E7%AB%99.mp4", "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E9%80%9A%E8%BF%87%E4%BA%BA%E8%A1%8C%E9%81%93.mp4",
            "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E8%B7%AF%E5%8F%A3%E5%8F%B3%E8%BD%AC%E5%BC%AF.mp4", "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E9%80%9A%E8%BF%87%E8%BF%87%E5%8F%A3.mp4",
            "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E7%9B%B4%E7%BA%BF%E8%A1%8C%E9%A9%B6.mp4", "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E8%B7%AF%E5%8F%A3%E5%B7%A6%E8%BD%AC%E5%BC%AF.mp4",
            "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E5%A4%9C%E9%97%B4%E8%A1%8C%E9%A9%B6.mp4", "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E9%9D%A0%E8%BE%B9%E5%81%9C%E8%BD%A6.mp4", "http://oekt4jwrq.bkt.clouddn.com/%E7%88%B1%E5%89%AA%E8%BE%91-%E8%B5%B7%E6%AD%A5.mp4"
            , "http://oekt4jwrq.bkt.clouddn.com/w0141uy9mgs.hd.mp4"};
    private String three_subject_text[] = {"上车准备", "变更车道", "会车", "加减档操作", "超车", "掉头", "通过学校", "通过公交车站", "通过人形道", "路口右转弯", "通过路口", "直线行驶 ", "路口左转弯", "夜间行驶", "靠边停车", "起步", "完整版"};
    private ViewPager mViewPager;
    private ActionBar actionBar;
    private GridView my_gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ActivityCollector.addActivity(this);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//设置返回箭头显示
        // actionBar.setDisplayUseLogoEnabled(false);//设置logo是否显示（默认为true哦）
        //当然有时候我们想要用其他的logo替换返回箭头,来做一些其他的操作
        actionBar.setHomeAsUpIndicator(R.drawable.top_button_use);


        mViewPager = (ViewPager) findViewById(R.id.container);
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.fragmenone, null));
        views.add(inflater.inflate(R.layout.fragmentwo, null));
        views.add(inflater.inflate(R.layout.fragmenthree, null));
        views.add(inflater.inflate(R.layout.fragmenfive, null));
        vpAdapter = new ViewPagerAdapter(views, this);
        mViewPager.setAdapter(vpAdapter);
        mViewPager.setOnPageChangeListener(this);

        Two_subjects1 = (ImageView) views.get(1).findViewById(R.id.Two_subjects1);
        Two_subjects2 = (ImageView) views.get(1).findViewById(R.id.Two_subjects2);
        Two_subjects3 = (ImageView) views.get(1).findViewById(R.id.Two_subjects3);
        Two_subjects4 = (ImageView) views.get(1).findViewById(R.id.Two_subjects4);
        Two_subjects5 = (ImageView) views.get(1).findViewById(R.id.Two_subjects5);
        one = (ImageView) findViewById(R.id.one);
        two = (ImageView) findViewById(R.id.two);
        three = (ImageView) findViewById(R.id.three);
        five = (ImageView) findViewById(R.id.five);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        five.setOnClickListener(this);
        Two_subjects1.setOnClickListener(this);
        Two_subjects2.setOnClickListener(this);
        Two_subjects3.setOnClickListener(this);
        Two_subjects4.setOnClickListener(this);
        Two_subjects5.setOnClickListener(this);

        starttext1 = (Button) views.get(0).findViewById(R.id.start);
        testrecord_one = (Button) views.get(0).findViewById(R.id.testrecord_one);
        startfive = (Button) views.get(3).findViewById(R.id.startfive);
        gotest_one = (Button) views.get(0).findViewById(R.id.gotest_one);
        gotest_five = (Button) views.get(3).findViewById(R.id.gotest_five);
        testrecord_five = (Button) views.get(3).findViewById(R.id.testrecord_five);

        my_gridView = (GridView) views.get(2).findViewById(R.id.my_grid_use);
        my_gridView.setAdapter(new MyAdpapter(this, three_subject_text));
        my_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                govideo(three_subject[position], "subject_three.txt", R.drawable.chebignan);
            }
        });

        starttext1.setOnClickListener(this);
        testrecord_one.setOnClickListener(this);
        startfive.setOnClickListener(this);
        gotest_one.setOnClickListener(this);
        gotest_five.setOnClickListener(this);
        testrecord_five.setOnClickListener(this);
        ButtunSimulation(starttext1);
        ButtunSimulation(startfive);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, PeopleActivity.class));
                return true;
            case R.id.about_my:
                startActivity(new Intent(MainActivity.this,WebActivity.class));
                return true;
            case R.id.mi:
                go_about_mi("about_My.txt");
                return true;
            case R.id.new_nan:
                startActivity(new Intent(this, RequiredActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(backFlag){
            //退出
            super.onBackPressed();
        }else{
            //单击一次提示信息
            showToast("双击退出");
            backFlag=true;
            new Thread(){
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //3秒之后，修改flag的状态
                    backFlag=false;
                };
            }.start();
        }
    }
    private void go_about_mi(String txt) {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putString("txt", txt);
        intent.putExtras(b);
        intent.setClass(MainActivity.this, MyaboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        restartBotton();

        switch (v.getId()) {
            case R.id.one:
                one.setImageResource(R.drawable.use_botton_upison);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.two:
                two.setImageResource(R.drawable.use_botton_upison);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.three:
                three.setImageResource(R.drawable.use_botton_upison);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.five:
                three.setImageResource(R.drawable.use_botton_upison);
                mViewPager.setCurrentItem(3);
                break;
            case R.id.startfive:
                Opensimulation("4", "simulation");
                break;
            case R.id.start:
                Opensimulation("1", "simulation");
                break;
            case R.id.testrecord_one:
                Open_Record("subjects1");
                break;
            case R.id.testrecord_five:
                Open_Record("subjects4");
                break;
            case R.id.gotest_one:
                Opensimulation("1", "test");
                break;
            case R.id.gotest_five:
                Opensimulation("4", "test");
                break;
            //倒车入库
            case R.id.Two_subjects1:
                govideo("http://oekt4jwrq.bkt.clouddn.com/%E5%80%92%E8%BD%A6%E5%85%A5%E5%BA%93.mp4", "Treasury.txt", R.drawable.ruku);
                break;
            //测方位
            case R.id.Two_subjects2:
                govideo("http://oekt4jwrq.bkt.clouddn.com/%E6%B5%8B%E6%96%B9%E4%BD%8D%E5%81%9C%E8%BD%A6%E8%A7%86%E9%A2%91.mp4", "Side_parking.txt", R.drawable.cemian);
                break;
            //曲线
            case R.id.Two_subjects3:
                govideo("http://oekt4jwrq.bkt.clouddn.com/%E6%9B%B2%E7%BA%BF%E8%A1%8C%E9%A9%B6.mp4", "curve.txt", R.drawable.quxiao);
                break;
            //直角
            case R.id.Two_subjects4:
                govideo("http://oekt4jwrq.bkt.clouddn.com/%E7%9B%B4%E8%A7%92%E8%BD%AC%E5%BC%AF.mp4", "Quarter_turn.txt", R.drawable.zhijiao);
                break;
            case R.id.Two_subjects5:
                govideo("http://oekt4jwrq.bkt.clouddn.com/%E4%B8%8A%E5%9D%A1%E8%B7%AF%E5%AE%9A%E7%82%B9%E5%81%9C%E8%BD%A6%E4%B8%8E%E5%9D%A1%E9%81%93%E8%B5%B7%E6%AD%A5.mp4", "Broken_bits.txt", R.drawable.shangpo);
                break;
        }
    }

    //打开视频播放函数
    private void govideo(final String url, final String text, final int recouse) {
        b.putString("Url", url);
        b.putString("text", text);
        b.putInt("id", recouse);
        intent.putExtras(b);
        intent.setClass(MainActivity.this, UsevideoActivity.class);
        startActivity(intent);
    }

    protected void Open_Record(String subject) {
        b.putString("subject", subject);
        intent.putExtras(b);
        intent.setClass(MainActivity.this, RecordActivity.class);
        startActivity(intent);
    }

    //设置按钮动画
    private void ButtunSimulation(Button buttonsemlation) {
        //Image
        Drawable m_D1 = MainActivity.this.getResources().getDrawable(R.drawable.gosimulation);
        Drawable m_D2 = MainActivity.this.getResources().getDrawable(R.drawable.gosimulationthree);
        Drawable m_D3 = MainActivity.this.getResources().getDrawable(R.drawable.gosimulationtwo);
        Drawable m_D4 = MainActivity.this.getResources().getDrawable(R.drawable.gosimulationfive);
        final AnimationDrawable m_AnimaD = new AnimationDrawable();//使用动画布局
        m_AnimaD.addFrame(m_D1, 300);
        m_AnimaD.addFrame(m_D2, 300);
        m_AnimaD.addFrame(m_D3, 300);
        m_AnimaD.addFrame(m_D4, 300);
        m_AnimaD.setOneShot(false);//设置循环播放帧数
        buttonsemlation.setBackgroundDrawable(m_AnimaD);
//        rpInter.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        starttext1.setLayoutParams(rpInter);
        buttonsemlation.postDelayed(new Runnable() {//新开启一个线程来播放动画按钮
            public void run() {
                m_AnimaD.start();
            }
        }, 1000);//使用延迟保证start()之前view已经设置drawable生效

    }

    private void Opensimulation(String s, String type) {
        b.putString("subject", s);
        b.putString("type", type);
        intent.putExtras(b);
        intent.setClass(MainActivity.this, SelectActivity.class);
        startActivity(intent);
    }

    private void showToast(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void restartBotton() {
        one.setImageResource(R.drawable.use_bottom_up);
        two.setImageResource(R.drawable.use_bottom_up);
        three.setImageResource(R.drawable.use_bottom_up);
        five.setImageResource(R.drawable.use_bottom_up);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        restartBotton();
        switch (position) {
            case 0:
                one.setImageResource(R.drawable.use_botton_upison);
                break;
            case 1:
                two.setImageResource(R.drawable.use_botton_upison);
                break;
            case 2:
                three.setImageResource(R.drawable.use_botton_upison);
                break;
            case 3:
                five.setImageResource(R.drawable.use_botton_upison);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
