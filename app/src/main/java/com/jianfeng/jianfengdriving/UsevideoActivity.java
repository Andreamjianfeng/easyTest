package com.jianfeng.jianfengdriving;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class UsevideoActivity extends AppCompatActivity {
private ImageView imageView,useimgid;
    private TextView textView;
    private boolean have_wife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usevideo);
                Toolbar toolbar = (Toolbar) findViewById(R.id.usevideo_toolbar);
        toolbar.setTitle("视频教学");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        final String url = data.getString("Url");
        final String text = data.getString("text");
        final int imgid = data.getInt("id");
        imageView= (ImageView) findViewById(R.id.usevideoplay);
        useimgid= (ImageView) findViewById(R.id.uesvideoimg);
        useimgid.setImageResource(imgid);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                mWifi.isConnected();
                if (mWifi != null && mWifi.isConnected()) {
                    System.out.println("有网");
                    have_wife = true;
                } else {
                    System.out.println("无网");
                    have_wife = false;
                }
                if (have_wife){
                    Intent intent=new Intent();
                    Bundle b=new Bundle ();
                    b.putString("Url", url);
                    intent.putExtras(b);
                    intent.setClass(UsevideoActivity.this, VideoActivity.class);
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UsevideoActivity.this);
                    builder.setMessage("亲，Wift没有连接，是否继续");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent();
                            Bundle b=new Bundle ();
                            b.putString("Url", url);
                            intent.putExtras(b);
                            intent.setClass(UsevideoActivity.this, VideoActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }

            }
        });
        try {
//Return an AssetManager instance for your application's package
            InputStream is = getAssets().open(text);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text1= new String(buffer, "UTF-8");
            // Finally stick the string into the text view.
            TextView tv = (TextView) findViewById(R.id.usevideotext);
            tv.setText(text1);
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
}
