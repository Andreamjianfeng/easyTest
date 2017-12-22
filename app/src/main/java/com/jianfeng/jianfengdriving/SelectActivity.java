package com.jianfeng.jianfengdriving;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectActivity extends AppCompatActivity {
    private String subject = "null", model, type;
    private String[] strings = {"a1", "a2", "b1", "b2", "c1", "c2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.select_toolbar);
        toolbar.setTitle("选择类型");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initview();
    }

    private void initview() {
        ListView listView = (ListView) findViewById(R.id.listselect);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        subject = data.getString("subject");
        type = data.getString("type");
        listView.setAdapter(new listAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model = strings[position];
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("subject", subject);
                b.putString("model", model);
                intent.putExtras(b);
                if (type.equals("test"))
                    intent.setClass(SelectActivity.this, TestActivity.class);
                else
                    intent.setClass(SelectActivity.this, SimulationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public class listAdapter extends BaseAdapter {
        public listAdapter() {

        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(SelectActivity.this); //MainActivity.instance是在MainActivity.java中定义的，public static MainActivity instance;
            View myLoginView = layoutInflater.inflate(R.layout.list_use, null);
            TextView textView = (TextView) myLoginView.findViewById(R.id.listtext);
            String leixin = strings[position] + "类型";
            textView.setText(leixin);
            return myLoginView;
        }
    }
}
