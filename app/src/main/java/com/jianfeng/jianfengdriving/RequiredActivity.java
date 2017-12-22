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

public class RequiredActivity extends AppCompatActivity {
    private String[] strings = {"新手必读——送给新手驾驶员上路的29条注意事项！", "什么是驾照", "法律法规","领证过程","考试","驾照管理","如何计分","扣留驾照","换证补证","计分审验"};
    private String[] stringtxt = {"a_novice.txt", "introduced.txt", "law.txt","license_process.txt","test.txt","management.txt","scoring.txt","detained.txt","replacement.txt","Scoring_cb.txt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required);
        Toolbar toolbar = (Toolbar) findViewById(R.id.required_toolbar);
        toolbar.setTitle("新手指南");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ListView listView = (ListView) findViewById(R.id.requiredlist);
        listView.setAdapter(new listAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("txt", stringtxt[position]);
                intent.putExtras(b);
                intent.setClass(RequiredActivity.this, MyaboutActivity.class);
                startActivity(intent);
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
            LayoutInflater layoutInflater = LayoutInflater.from(RequiredActivity.this); //MainActivity.instance是在MainActivity.java中定义的，public static MainActivity instance;
            View myLoginView = layoutInflater.inflate(R.layout.list_use, null);
            TextView textView = (TextView) myLoginView.findViewById(R.id.listtext);
            String leixin = strings[position] ;
            textView.setText(leixin);
            return myLoginView;
        }
    }
}