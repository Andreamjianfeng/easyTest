package com.jianfeng.jianfengdriving;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.jianfeng.jianfengdriving.DB.DbSql;
import com.jianfeng.jianfengdriving.DB.Whatsubjects;
import com.jianfeng.jianfengdriving.Myjava.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {
private MyListView recordlist;
    private  SimpleAdapter adapter;
    private String subject=null;
    private  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private  final Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.record_toolbar);
        toolbar.setTitle("考试记录");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle data = getIntent().getExtras();
        subject = data.getString("subject");
        recordlist = (MyListView) findViewById(R.id.listrecord);
        adapteruse();
        recordlist.setAdapter(adapter);
        recordlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String deleteScore = list.get(position).get("score").toString();
                Bundle b = new Bundle();
                b.putInt("score", Integer.parseInt(deleteScore));
                b.putString("bool","noSql");
                intent.putExtras(b);
                intent.setClass(RecordActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });
        recordlist.setDelButtonClickListener(new MyListView.DelButtonClickListener() {
            @Override
            public void clickHappend(int position) {
                String deleteTime = list.get(position).get("time").toString();
                Whatsubjects whatsubjects = new Whatsubjects(getApplicationContext());
                int temp = whatsubjects.deleteDate(deleteTime,subject);
                if (temp != -1) {
                    System.out.println(temp);
                } else {
                    System.out.println("失败");
                }
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
private  void adapteruse(){
       adapter = new SimpleAdapter(this, getData(),
            R.layout.list_use_record, new String[] { "score", "time" },
            new int[] { R.id.recordtextscore, R.id.recordtexttime,});
}

    private List<Map<String, Object>> getData() {

        Map<String, Object> map = new HashMap<String, Object>();
        DbSql dbSql = new DbSql(this);
        SQLiteDatabase sqLiteDatabase = dbSql.getWritableDatabase();
        Cursor cur = sqLiteDatabase.query(subject, null, null, null, null, null, null);
        while (cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                cur.moveToPosition(i);
                String scroe =cur.getString(2);
                String time ="时间："+ cur.getString(1);
                map = new HashMap<String, Object>();
                map.put("score", scroe);
                map.put("time", time);
                list.add(map);
            }
        }
        return list;
    }
}
