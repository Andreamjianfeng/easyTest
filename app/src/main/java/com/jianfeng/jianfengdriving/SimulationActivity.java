package com.jianfeng.jianfengdriving;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioButton RA, RB, RC, RD;
    private LinearLayout lyradio, lycheckbox;
    private CheckBox A, B, C, D;
    private Button answertemp, ontitle, nexttitle, tempbty;
    private ImageView pic;
    private JSONObject timu;
    private Boolean haveTimu;
    private JSONArray timuArry;
    private TextView detail, index;
    private List<String> result = new ArrayList<>();
    private int IndexTimu = 0, score = 0, temp_timu = 0;
    private AlertDialog.Builder dialogshow;
    private String answer, explains, picurl;
    private String subject, model;
    private LoadingProgressDialog dialog;
    public static final String APPKEY = "d4fd9c6a9480ad255b4f6b2d63135ab9";
    private RelativeLayout main_re;
    private int startx,starty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.simulation_toolbar);
        toolbar.setTitle("开始做题");
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
        haveTimu = false;
        inti();
        dialog = new LoadingProgressDialog(SimulationActivity.this, "正在加载中...", R.drawable.frame);
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Getthetitle();
            }
        }).start();
    }

    private void inti() {
        RA = (RadioButton) findViewById(R.id.radiochooseA);
        RB = (RadioButton) findViewById(R.id.radiochooseB);
        RC = (RadioButton) findViewById(R.id.radiochooseC);
        RD = (RadioButton) findViewById(R.id.radiochooseD);

        lyradio = (LinearLayout) findViewById(R.id.oneradio);
        lycheckbox = (LinearLayout) findViewById(R.id.morecheckbox);
        pic = (ImageView) findViewById(R.id.shitu);
        A = (CheckBox) findViewById(R.id.checkBoxchooseA);
        B = (CheckBox) findViewById(R.id.checkBoxchooseB);
        C = (CheckBox) findViewById(R.id.checkBoxchooseC);
        D = (CheckBox) findViewById(R.id.checkBoxchooseD);

        index = (TextView) findViewById(R.id.whicharticle);
        detail = (TextView) findViewById(R.id.titlesaid);
        answertemp = (Button) findViewById(R.id.answertemp);
        ontitle = (Button) findViewById(R.id.ontitle);
        tempbty = (Button) findViewById(R.id.tempbty);
        nexttitle = (Button) findViewById(R.id.nexttitle);
        main_re = (RelativeLayout) findViewById(R.id.main_re);

        lyradio.setVisibility(View.GONE);
        lycheckbox.setVisibility(View.GONE);
        answertemp.setOnClickListener(this);
        ontitle.setOnClickListener(this);
        pic.setOnClickListener(this);
        nexttitle.setOnClickListener(this);
        tempbty.setOnClickListener(this);
        main_re.setOnClickListener(this);
        main_re.setOnTouchListener(new View.OnTouchListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answertemp:
                dialogsolution();
                break;
            case R.id.ontitle:
                IndexTimu--;
                questions();
                break;
            case R.id.main_re:
                break;
            case R.id.nexttitle:
                IndexTimu++;
                questions();
                break;
            case R.id.tempbty:
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putInt("score", score);
                subject = "subjects" + subject;
                b.putString("subject", subject);
                b.putString("bool", "addSql");
                intent.putExtras(b);
                intent.setClass(this, ScoreActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.toggle:
                finish();
                break;
            case R.id.shitu:
                try {
                    JSONObject timu1 = timuArry.getJSONObject(IndexTimu);
                    picurl = timu1.getString("url");
                    Intent change = new Intent();
                    Bundle cb = new Bundle();
                    cb.putString("change", picurl);
                    change.putExtras(cb);
                    change.setClass(this, ChangeImg.class);
                    startActivity(change);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void questions() {
        temp_timu = IndexTimu + 1;
        if (haveTimu && (IndexTimu < 100)) {
            if (IndexTimu < 0) {
                shoumsg("没有上一题");
                IndexTimu = 0;
            } else if (IndexTimu > 0) {
                deal();
            }
            try {
                JSONObject timu1 = timuArry.getJSONObject(IndexTimu);
                detail.setText("        " + timu1.getString("question"));
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
                    Picasso.with(SimulationActivity.this).load(picurl).into(pic);
                } else {
                    pic.setVisibility(View.GONE);
                }
                if (!item1.equals("")) {
                    if (item1.equals("正确") || item2.equals("正确")) {
                        lyradio.setVisibility(View.VISIBLE);
                        lycheckbox.setVisibility(View.GONE);
                        index.setText("<判断题>\t\t\t第" + temp_timu + "题");
                        RA.setText(item1);
                        RB.setText(item2);
                        RC.setVisibility(View.GONE);
                        RD.setVisibility(View.GONE);     //隐藏按钮
                    } else if (answer.equals("1") || answer.equals("2") || answer.equals("3") || answer.equals("4")) {
                        lyradio.setVisibility(View.VISIBLE);
                        lycheckbox.setVisibility(View.GONE);
                        RC.setVisibility(View.VISIBLE);
                        RD.setVisibility(View.VISIBLE);     //隐藏按钮
                        index.setText("<单选题>\t\t\t第" + temp_timu + "题");
                        RA.setText(item1);
                        RB.setText(item2);
                        RC.setText(item3);
                        RD.setText(item4);
                    } else {
                        lyradio.setVisibility(View.GONE);
                        lycheckbox.setVisibility(View.VISIBLE);
                        index.setText("<多选题>\t\t\t第" + temp_timu + "题");
                        A.setText(item1);
                        B.setText(item2);
                        C.setText(item3);
                        D.setText(item4);
                    }
                } else {
                    nexttitle.callOnClick();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            tempbty.callOnClick();
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

    private void shoumsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void dialogsolution() {
        LayoutInflater layoutInflater = LayoutInflater.from(this); //MainActivity.instance是在MainActivity.java中定义的，public static MainActivity instance;
        View myLoginView = layoutInflater.inflate(R.layout.dialog_solution, null);
        TextView texttoggle = (TextView) myLoginView.findViewById(R.id.solutiondialog);
        texttoggle.setText("\n" + "        " + explains + "\n");
        dialogshow = new AlertDialog.Builder(SimulationActivity.this);
        dialogshow.setView(myLoginView);
        dialogshow.setIcon(R.drawable.bacaact);
        dialogshow.setTitle("提示");
        dialogshow.show();
    }

    private void dialogshow() {
        LayoutInflater layoutInflater = LayoutInflater.from(this); //MainActivity.instance是在MainActivity.java中定义的，public static MainActivity instance;
        View myLoginView = layoutInflater.inflate(R.layout.dialog, null);
        Button mytoggle = (Button) myLoginView.findViewById(R.id.toggle);
        mytoggle.setOnClickListener(this);
        AlertDialog.Builder dialogshow = new AlertDialog.Builder(SimulationActivity.this);
        dialogshow.setView(myLoginView);
        dialogshow.setIcon(R.drawable.nowifi_net);
        dialogshow.setTitle("无网络连接");
        dialogshow.show();
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
            score++;
        }
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
//                    System.out.println(params[0]);
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
                } else {
                    dialogshow();
                }
                dialog.dismiss();

                shoumsg(result);
            }
        }.execute("http://api2.juheapi.com/jztk/query?" + detail);
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

}
