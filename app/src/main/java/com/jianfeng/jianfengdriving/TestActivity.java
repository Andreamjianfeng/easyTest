package com.jianfeng.jianfengdriving;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jianfeng.jianfengdriving.Myjava.LoadingProgressDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioButton RA, RB, RC, RD;
    private LinearLayout lyradio, lycheckbox;
    private CheckBox A, B, C, D;
    private Button reflash, confirm, next,backtitle;
    private ImageView pic;
    private JSONObject timu;
    private Boolean haveTimu;
    private JSONArray timuArry;
    private TextView detail, index;
    private int IndexTimu = 0;
    private String answer, explains, picurl;
    private String subject, model;
    public static final String APPKEY = "d4fd9c6a9480ad255b4f6b2d63135ab9";
    private LoadingProgressDialog dialog;
    private AlertDialog.Builder dialogshow;
    private RelativeLayout test_re;
    private int startx,starty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.test_toolbar);
        toolbar.setTitle("开始刷题");
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
        subject = data.getString("subject");
        model = data.getString("model");
        initview();
        haveTimu = false;
        dialog = new LoadingProgressDialog(TestActivity.this, "正在加载中...", R.drawable.frame);
        dialog.show();
//        dialog.dismiss();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Getthetitle();
            }
        }).start();
    }

    private void initview() {
        RA = (RadioButton) findViewById(R.id.radioAchoose);
        RB = (RadioButton) findViewById(R.id.radioBchoose);
        RC = (RadioButton) findViewById(R.id.radioCchoose);
        RD = (RadioButton) findViewById(R.id.radioDchoose);
        lyradio = (LinearLayout) findViewById(R.id.lyradio);
        lycheckbox = (LinearLayout) findViewById(R.id.lycheckbox);
        pic = (ImageView) findViewById(R.id.pic);
        A = (CheckBox) findViewById(R.id.checkBoxA);
        B = (CheckBox) findViewById(R.id.checkBoxB);
        C = (CheckBox) findViewById(R.id.checkBoxC);
        D = (CheckBox) findViewById(R.id.checkBoxD);

        index = (TextView) findViewById(R.id.index);
        detail = (TextView) findViewById(R.id.detail);

        reflash = (Button) findViewById(R.id.reflash);
        backtitle = (Button) findViewById(R.id.backtitle);
        confirm = (Button) findViewById(R.id.confirm);
        next = (Button) findViewById(R.id.next);
        test_re = (RelativeLayout) findViewById(R.id.test_re);

        lyradio.setVisibility(View.GONE);
        lycheckbox.setVisibility(View.GONE);
        next.setOnClickListener(this);
        pic.setOnClickListener(this);
        confirm.setOnClickListener(this);
        backtitle.setOnClickListener(this);
        reflash.setOnClickListener(this);
        test_re.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int rawx = (int) (event.getRawX());
                int rawy = (int) (event.getRawY());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startx=rawx;
                        starty=rawy;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        int Move_distance =startx-rawx;
                        if(Move_distance>160)
                        {
                            IndexTimu++;
                            questions();
                        }
                        if(Move_distance<-160)
                        {
                            IndexTimu--;
                            questions();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void shoumsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void Getthetitle() {
        Map params1 = new HashMap();//请求参数
        params1.put("key", APPKEY);//您申请的appKey
        params1.put("subject", subject);//选择考试科目类型，1：科目1；4：科目4
        params1.put("model", model);//驾照类型，可选择参数为：c1,c2,a1,a2,b1,b2；当subject=4时可省略
        params1.put("testType", "rand");//测试类型，rand：随机测试（随机100个题目），order：顺序测试（所选科目全部题目）
        String detail = urlencode(params1);
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                try {

                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setUseCaches(false);
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setInstanceFollowRedirects(false);
                    System.out.println(params[0]);
                    connection.connect();
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    String results = null;
                    StringBuilder builder = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        builder.append(line).append("\n");

                    }
                    br.close();
                    isr.close();
                    is.close();
                    timu = new JSONObject(builder.toString());
                    if (!timu.getString("result").equals("null")) {
                        haveTimu = true;
                        timuArry = timu.getJSONArray("result");
                        IndexTimu = 0;
                        return "获取题目成功,请答题！";
                    } else {
                        return "无网络连接！";
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return "获取题目失败！";
                } catch (IOException e) {
                    e.printStackTrace();
                    return "获取题目失败！";
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "获取题目失败！";
                }
            }

            protected void onPostExecute(String result) {
                if (haveTimu) {
                    questions();
                } else
                    dialogshow();
                dialog.dismiss();
            }
        }.execute("http://api2.juheapi.com/jztk/query?" + detail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                IndexTimu++;
                questions();
                break;
            case R.id.backtitle:
                IndexTimu--;
                questions();
                break;
            case R.id.confirm:
                deal();
                break;
            case R.id.reflash:
                dialog.show();
                Getthetitle();
                break;
            case R.id.toggle:
                finish();
                break;
            case R.id.pic:
                try {
                    JSONObject timu1 = timuArry.getJSONObject(IndexTimu);
                    picurl = timu1.getString("url");
                    Intent change = new Intent();
                    Bundle cb = new Bundle();
                    cb.putString("change",picurl);
                    change.putExtras(cb);
                    change.setClass(this, ChangeImg.class);
                    startActivity(change);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    private void dialogsolution(String judge,int resoure) {
        LayoutInflater layoutInflater = LayoutInflater.from(this); //MainActivity.instance是在MainActivity.java中定义的，public static MainActivity instance;
        View myLoginView = layoutInflater.inflate(R.layout.dialog_solution, null);
        TextView texttoggle = (TextView) myLoginView.findViewById(R.id.solutiondialog);
        texttoggle.setText("\n" + "        " + explains + "\n");
        dialogshow = new AlertDialog.Builder(TestActivity.this);
        dialogshow.setView(myLoginView);
        dialogshow.setIcon(resoure);
        dialogshow.setTitle(judge);
        dialogshow.show();
    }

    private void dialogshow() {
        LayoutInflater layoutInflater = LayoutInflater.from(this); //MainActivity.instance是在MainActivity.java中定义的，public static MainActivity instance;
        View myLoginView = layoutInflater.inflate(R.layout.dialog, null);
        Button mytoggle = (Button) myLoginView.findViewById(R.id.toggle);
        mytoggle.setOnClickListener(this);
        dialogshow = new AlertDialog.Builder(TestActivity.this);
        dialogshow.setView(myLoginView);
        dialogshow.setIcon(R.drawable.bacaact);
        dialogshow.setTitle("无网络连接");
        dialogshow.show();
    }

    private void questions() {
        if (haveTimu && (IndexTimu < 10)) {
            if (IndexTimu < 0) {
                shoumsg("没有上一题");
                IndexTimu = 0;
            }
            try {
                JSONObject timu1 = timuArry.getJSONObject(IndexTimu);
                detail.setText("        "+timu1.getString("question"));
                String item1, item2, item3, item4;
                item1 = timu1.getString("item1");
                item2 = timu1.getString("item2");
                item3 = timu1.getString("item3");
                item4 = timu1.getString("item4");
                answer = timu1.getString("answer");
                explains = timu1.getString("explains");
                picurl = timu1.getString("url");
                if (!picurl.equals("")) {
                    pic.setVisibility(View.VISIBLE);
                    Picasso.with(TestActivity.this).load(picurl).into(pic);
                } else {
                    pic.setVisibility(View.GONE);
                }
                if(!item1.equals("")) {
                    if (item1.equals("正确") || item2.equals("正确")) {
                        lyradio.setVisibility(View.VISIBLE);
                        lycheckbox.setVisibility(View.GONE);
                        index.setText("<判断题>");
                        RA.setText(item1);
                        RB.setText(item2);
                        RC.setVisibility(View.GONE);
                        RD.setVisibility(View.GONE);     //隐藏按钮
                    } else if (answer.equals("1") || answer.equals("2") || answer.equals("3") || answer.equals("4")) {
                        lyradio.setVisibility(View.VISIBLE);
                        lycheckbox.setVisibility(View.GONE);
                        RC.setVisibility(View.VISIBLE);
                        RD.setVisibility(View.VISIBLE);     //隐藏按钮
                        index.setText("<单选题>");
                        RA.setText(item1);
                        RB.setText(item2);
                        RC.setText(item3);
                        RD.setText(item4);
                    } else {
                        lyradio.setVisibility(View.GONE);
                        lycheckbox.setVisibility(View.VISIBLE);
                        index.setText("<多选题>");
                        A.setText(item1);
                        B.setText(item2);
                        C.setText(item3);
                        D.setText(item4);
                    }
                }
                else {
                    next.callOnClick();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            shoumsg("没题目啦！请获取题目");
        }
        RA.setChecked(false);
        RB.setChecked(false);
        RC.setChecked(false);
        RD.setChecked(false);
        D.setChecked(false);
        C.setChecked(false);
        B.setChecked(false);
        A.setChecked(false);
    }

    private void deal() {

        int answers = 0;
        if (RA.isChecked() || A.isChecked()) {
            answers = answers + 1;
        }
        if (RB.isChecked() || B.isChecked()) {
            answers = answers + 2;
        }
        if (RC.isChecked() || C.isChecked()) {
            answers = answers + 3;
        }
        if (RD.isChecked() || D.isChecked()) {
            answers = answers + 4;
        }
        if (answer.equals(Integer.toString(answers))) {
            dialogsolution("正确",R.drawable.yes_title_buttun);
        } else {
            dialogsolution("错误",R.drawable.no_title_buttun);
        }
    }
}
