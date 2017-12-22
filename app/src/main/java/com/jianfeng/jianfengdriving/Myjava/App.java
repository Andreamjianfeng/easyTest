package com.jianfeng.jianfengdriving.Myjava;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jianfeng.jianfengdriving.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.ninegrid.NineGridView;

/**
 * Created by 吴剑锋 on 2017/4/30.
 * app类
 */

public class App extends Application {
    public MyUser myUser;
    public static Typeface TEXT_TYPE ;
    private class GlideImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_image)//
                    .error(R.drawable.ic_default_image)//
                    .into(imageView);
        }
        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        NineGridView.setImageLoader(new GlideImageLoader());
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        inti_text();
    }
    /**
     * 初始化字体
     */
    private void inti_text(){
        try{
            TEXT_TYPE = Typeface.createFromAsset(getAssets(),"fonts/new_text.ttf");
        }catch(Exception e){
            Log.i("MyApp","加载第三方字体失败。") ;
            TEXT_TYPE = null ;
        }
    }
}
