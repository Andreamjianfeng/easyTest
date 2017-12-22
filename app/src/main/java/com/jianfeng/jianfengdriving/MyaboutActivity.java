package com.jianfeng.jianfengdriving;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MyaboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myabout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myabout_toolbar);
        toolbar.setTitle("文字介绍");
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
        String txt = data.getString("txt");
        System.out.println(txt);
        try {
//Return an AssetManager instance for your application's package
            InputStream is = getAssets().open(txt);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text1= new String(buffer, "UTF-8");
            // Finally stick the string into the text view.
            TextView tv = (TextView) findViewById(R.id.text11);
            tv.setText(text1);
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
}
