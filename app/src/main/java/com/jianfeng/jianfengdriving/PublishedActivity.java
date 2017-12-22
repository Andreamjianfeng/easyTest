package com.jianfeng.jianfengdriving;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jianfeng.jianfengdriving.Myjava.App;
import com.jianfeng.jianfengdriving.Myjava.FriendSay;
import com.jianfeng.jianfengdriving.Myjava.ImageLoader;
import com.jianfeng.jianfengdriving.Myjava.Tools;
import com.jianfeng.jianfengdriving.Tools.CircleTransform;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.ninegrid.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class PublishedActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView published_no, published_ok;
    private EditText published_say_tv;
    private RelativeLayout published_loading_re;
    private ArrayList<ImageItem> imageItems = new ArrayList<>();
    private int size = 0;
    private App app;
    private String my_say;
    private GridView my_grid;
    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);
        inti_view();
    }

    /**
     * 初始化view
     */
    private void inti_view() {
        published_no = (TextView) findViewById(R.id.published_no);
        published_ok = (TextView) findViewById(R.id.published_ok);
        published_say_tv = (EditText) findViewById(R.id.published_say_tv);
        my_grid = (GridView) findViewById(R.id.my_grid);
        published_loading_re = (RelativeLayout) findViewById(R.id.published_loading_re);
        published_no.setOnClickListener(this);
        published_ok.setOnClickListener(this);
        app = (App) getApplication();
        my_grid = (GridView) findViewById(R.id.my_grid);
        gridAdapter = new GridAdapter();
        my_grid.setAdapter(gridAdapter);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.published_no:
                finish();
                break;
            case R.id.published_ok:
                my_say = published_say_tv.getText().toString();
                published_say_tv.setText("");
                if (my_say.length() < 1 && size == 0) {
                    Tools.show_data(this, "发表不能为空");
                } else {
                    published_ok.setEnabled(false);
                    published_loading_re.setVisibility(View.VISIBLE);
                    Tools.show_data(this, "正在发表");
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            upload_database(v);
                        }
                    }.start();
                }
                break;
        }
    }

    /**
     * 添加图片
     */
    private void add_Image() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoader());
        imagePicker.setMultiMode(true);   //多选
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSelectLimit(6);    //最多选择X张
        imagePicker.setCrop(false);       //不进行裁剪
        Intent intent = new Intent(PublishedActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }

    /**
     * 上传说说
     */
    private void upload_database(final View view) {
        final FriendSay say = new FriendSay();
        say.setSay_say(my_say);
        say.setSay_praises(null);
        say.setSay_comments(null);
        say.setMyUser(app.myUser);
        if (size == 0) {
            say.setHave_icon(false);
            say.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                     if(e==null)
                     {
                         finish();
                     }
                }
            });
            return;
        }
        size = 0;
        final String[] filePaths = new String[imageItems.size()];
        for (int i = 0; i < imageItems.size(); i++) {
            filePaths[i] = imageItems.get(i).path;
        }
        say.setHave_icon(true);
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if (list1.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                    say.setSay_image(list1);
                    say.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            published_loading_re.setVisibility(View.GONE);
                            published_loading_re.setVisibility(View.GONE);
                            if (e == null) {
                                Tools.showSnackar(view,"上传成功");
                                finish();
                            }
                        }
                    });
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {
            }

            @Override
            public void onError(int i, String s) {
                published_loading_re.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                size=imageItems.size();
                gridAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private class GridAdapter extends BaseAdapter {
        public GridAdapter() {
        }

        @Override
        public int getCount() {
            if (imageItems == null)
                return 1;
            else
                return imageItems.size()+1;
        }

        @Override
        public Object getItem(int i) {
            return imageItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            GridAdapter.ViewHolder holder = null;
            if (view == null) {
                holder = new GridAdapter.ViewHolder();
                view = LayoutInflater.from(PublishedActivity.this).inflate(R.layout.grid_layout, null);
                holder.image_voice = (ImageView) view.findViewById(R.id.gird_img);
                view.setTag(holder);
            } else {
                holder = (GridAdapter.ViewHolder) view.getTag();
            }
            if (imageItems == null) {
                holder.image_voice.setImageResource(R.drawable.add_icon);
            } else {
                if (i == imageItems.size()) {
                    holder.image_voice.setImageResource(R.drawable.add_icon);
                } else {
                    File file = new File(imageItems.get(i).path);
                    if (file.exists()) {
                        Bitmap bm = BitmapFactory.decodeFile(imageItems.get(i).path);
                        holder.image_voice.setImageBitmap(CircleTransform.centerSquareScaleBitmap(bm,100));
                    }
                }
            }
            holder.image_voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((imageItems != null && i == imageItems.size()) || imageItems == null) {
                        add_Image();
                    }
                }
            });
            return view;
        }

        class ViewHolder {
            private ImageView image_voice;
        }
    }

}
