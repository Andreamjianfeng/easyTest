package com.jianfeng.jianfengdriving;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jianfeng.jianfengdriving.Myjava.App;
import com.jianfeng.jianfengdriving.Myjava.CircleTransform;
import com.jianfeng.jianfengdriving.Myjava.EditTextWithDel;
import com.jianfeng.jianfengdriving.Myjava.ImageLoader;
import com.jianfeng.jianfengdriving.Myjava.MyUser;
import com.jianfeng.jianfengdriving.Myjava.NameDialog;
import com.jianfeng.jianfengdriving.Myjava.Tools;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.ninegrid.ImageInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 *
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView setting_back_icon, setting_icon;
    private TextView setting_phone, setting_about, setting_name;
    private RelativeLayout re_icon, re_name, re_phone, re_about,loading_re;
    private App app;
    private View setting_loadingView;
    private ArrayList<ImageItem> imageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        inti_view();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inti_data();
    }

    /**
     * 初始化控件
     */
    private void inti_view() {
        setting_back_icon = (ImageView) findViewById(R.id.setting_back_av);
        setting_back_icon.setOnClickListener(this);
        setting_icon = (ImageView) findViewById(R.id.setting_icon);
        setting_phone = (TextView) findViewById(R.id.setting_phone);
        setting_about = (TextView) findViewById(R.id.setting_about);
        setting_name = (TextView) findViewById(R.id.setting_name);
        re_icon = (RelativeLayout) findViewById(R.id.re_icon);
        re_name = (RelativeLayout) findViewById(R.id.re_name);
        re_phone = (RelativeLayout) findViewById(R.id.re_phone);
        re_about = (RelativeLayout) findViewById(R.id.re_about);
        loading_re = (RelativeLayout) findViewById(R.id.loading_re);
        setting_loadingView = findViewById(R.id.setting_loadingView);
        re_name.setOnClickListener(this);
        re_about.setOnClickListener(this);
        re_icon.setOnClickListener(this);
        re_phone.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void inti_data() {
        app = (App) getApplication();
        Picasso.with(this).load(app.myUser.getMy_icon()).into(setting_icon);
        setting_name.setText(app.myUser.getMy_name());
        setting_phone.setText(app.myUser.getMobilePhoneNumber());
    }

    /**
     * 更改头像
     */
    private void change_icon() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoader());
        imagePicker.setMultiMode(false);   //多选
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSelectLimit(1);    //最多选择X张
        imagePicker.setCrop(true);       //不进行裁剪
        Intent intent = new Intent(SettingActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                loading_re.setVisibility(View.VISIBLE);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        save_icon();
                    }
                }.start();
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 保存图片
     */
    private void save_icon() {
        final BmobFile bmobFile=new BmobFile(new File(imageItems.get(0).path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Picasso.with(SettingActivity.this).load(bmobFile.getFileUrl()).into(setting_icon);
                    MyUser myUser =new MyUser();
                    myUser.setMy_icon(bmobFile.getFileUrl());
                    app.myUser.setMy_icon(bmobFile.getFileUrl());
                    save_data(myUser);
                    loading_re.setVisibility(View.GONE);
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }
        });
    }

    /**
     * 修改名字Dialog
     */
    private void name_dialog(String top_text, String tv, final String name, final boolean who) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.name_dialog, (ViewGroup) findViewById(R.id.customDialog));
        final TextView name_dialog_name = (TextView) view.findViewById(R.id.name_dialog_name);
        name_dialog_name.setText(top_text);
        final TextView name_dialog_tv = (TextView) view.findViewById(R.id.name_dialog_tv);
        name_dialog_tv.setText(tv);
        final EditText editTextWithDel = (EditText) view.findViewById(R.id.name_dialog_edi);
        editTextWithDel.setText(name);
        final Button can_dialog_bty = (Button) view.findViewById(R.id.can_dialog_bty);
        final Button ok_dialog_bty = (Button) view.findViewById(R.id.ok_dialog_bty);
        final NameDialog nameDialog = new NameDialog(this, 0, 0, view, R.style.DialogTheme);
        nameDialog.show();
        can_dialog_bty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameDialog.cancel();
            }
        });
        ok_dialog_bty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String change_text = editTextWithDel.getText().toString();
                final MyUser myUser = new MyUser();
                if (who) {
                    setting_name.setText(change_text);
                    myUser.setMy_name(change_text);
                    app.myUser.setMy_name(change_text);
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        save_data(myUser);
                    }
                }.start();
                nameDialog.cancel();
            }
        });
    }

    /**
     * 保存用户修改的数据
     *
     * @param myUser
     */
    private void save_data(MyUser myUser) {
        myUser.update(app.myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                }
            }
        });
    }

    /**
     * @param v 点击事件监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_about:
                break;
            case R.id.re_icon:
                change_icon();
                break;
            case R.id.re_name:
                name_dialog("请输入昵称", "起个好听的名字,让好友更容易找到你!", app.myUser.getMy_name(), true);
                break;
            case R.id.re_phone:
                break;
            case R.id.setting_back_av:
                finish();
                break;
        }
    }
}
