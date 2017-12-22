package com.jianfeng.jianfengdriving.MyAdapter;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianfeng.jianfengdriving.R;

///**
// * Created by Administrator on 2016/11/30.
// */

public class MyAdpapter extends BaseAdapter {
    private Context context;
    private String three_subject_text[]=null;
    public MyAdpapter(Context context,String s[]){
        this.context=context;
        this.three_subject_text=s;
    }
    @Override
    public int getCount() {
        return three_subject_text.length;
    }

    @Override
    public Object getItem(int position) {
        return three_subject_text[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.mygridview, null);
       ImageView functionImage = (ImageView) convertView.findViewById(R.id.img_grid);
       TextView functionText = (TextView) convertView.findViewById(R.id.text_grid);
       functionImage.setImageResource(R.drawable.baidu_video);
        functionText.setText(three_subject_text[position]);
        return convertView;
    }
}
