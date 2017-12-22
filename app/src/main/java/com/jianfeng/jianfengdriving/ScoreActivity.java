package com.jianfeng.jianfengdriving;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jianfeng.jianfengdriving.DB.Whatsubjects;
import com.jianfeng.jianfengdriving.Myjava.NameDialog;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static okhttp3.internal.Internal.instance;

public class ScoreActivity extends AppCompatActivity {
    private TextView scoretext;
    private ImageView call;
    private RelativeLayout share_score;
    private static final String APP_ID="wxdd133ace8085c8a1";
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.score_toolbar);
        toolbar.setTitle("你的分数");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        we_chat();
        inti();
    }

    /**
     * a初始化微信
     */
    private void we_chat() {
         api= WXAPIFactory.createWXAPI(this,APP_ID,true);
        api.registerApp(APP_ID);
    }

    private void inti() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        final int texscore = b.getInt("score");
        String subject = b.getString("subject");
        String addSql = b.getString("bool");
        final String s1 = String.valueOf(texscore);
        scoretext = (TextView) findViewById(R.id.scoretext);
        scoretext.setText(s1);
        call = (ImageView) findViewById(R.id.call);
        share_score = (RelativeLayout) findViewById(R.id.share_score);
        if (texscore < 60) {
            call.setImageResource(R.drawable.killnan);
        } else if (texscore > 60 && texscore < 80) {
            call.setImageResource(R.drawable.carsmartnan);
        } else {
            call.setImageResource(R.drawable.chebignan);
        }
        if (addSql.equals("addSql")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String time = formatter.format(curDate);
            Whatsubjects whatsubjects = new Whatsubjects(getApplicationContext());
            long temp = whatsubjects.addDate(time, s1, subject);
            if (temp != -1) {
                System.out.println("成功");
            } else {
                System.out.println("失败");
            }
        }
        share_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog();
            }
        });
    }

    /**
     * 显示分享框
     */
    private void show_dialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.shape_score_layout, (ViewGroup) findViewById(R.id.shape_score_lin));
        ImageView we_chat_shape = (ImageView) view.findViewById(R.id.we_chat_shape);
        ImageView we_chat_friend = (ImageView) view.findViewById(R.id.we_chat_friend);
        ImageView circle_shape = (ImageView) view.findViewById(R.id.circle_shape);
        final NameDialog nameDialog = new NameDialog(this, 0, 0, view, R.style.DialogTheme);
        nameDialog.show();
        we_chat_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_we_chat();
            }
        });
        we_chat_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_we_chat_friend();
            }
        });
        circle_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_circle();
            }
        });
    }

    /**
     * 分享到微信盆友圈
     */
    private void share_we_chat() {

    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    /**
     * 分享到微信盆友
     */
    private void share_we_chat_friend() {

    }

    /**
     * 分享到圈子
     */
    private void share_circle() {

    }
}
