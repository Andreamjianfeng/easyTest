package com.jianfeng.jianfengdriving;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends Activity {
    private VideoView videoplay;
    private TextView videotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String url = data.getString("Url");
        videoplay= (VideoView) findViewById(R.id.videoplay);
//        videotext= (TextView) findViewById(R.id.videotext);
        Uri uri = Uri.parse(url);
//        VideoView videoView = (VideoView) views.get(1).findViewById(R.id.videoviewgo);
        videoplay.setMediaController(new MediaController(this));
        videoplay.setVideoURI(uri);
        videoplay.start();
    }
}
