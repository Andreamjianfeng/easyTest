package com.jianfeng.jianfengdriving.MyAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianfeng.jianfengdriving.Myjava.FriendSay;
import com.jianfeng.jianfengdriving.Myjava.MyUser;
import com.jianfeng.jianfengdriving.Myjava.Tools;
import com.jianfeng.jianfengdriving.R;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 吴剑锋 on 2017/5/5.
 * 朋友圈适配器
 */

public class FriendAdapter extends BaseAdapter {
    private List<FriendSay> orders;
    private Context context;
    private StringBuffer comments_tv = new StringBuffer();
    private StringBuffer good_tv = new StringBuffer();
    private List<String> say_good;
    private List<String> say_comments;
    private String my_name;
    private boolean think_good = false;


    public FriendAdapter(Context context, List<FriendSay> orders, String my_name) {
        this.orders = orders;
        this.context = context;
        this.my_name = my_name;
    }

    @Override
    public int getCount() {
        if (orders == null)
            return 0;
        else
            return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add_order(List<FriendSay> orders) {
        this.orders = orders;

    }

    public void remove_order() {
        orders.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.friend_say_layout, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.say_list_name_tv);
            holder.time = (TextView) convertView.findViewById(R.id.say_list_time_tv);
            holder.say = (TextView) convertView.findViewById(R.id.say_list_my_say);
            holder.icon = (ImageView) convertView.findViewById(R.id.say_list_icon);
            holder.say_list_edi = (EditText) convertView.findViewById(R.id.say_list_edi);
            holder.nineGrid = (NineGridView) convertView.findViewById(R.id.nineGrid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        say_good = orders.get(position).getSay_praises();
        say_comments = orders.get(position).getSay_comments();
        Picasso.with(context).load(orders.get(position).getMyUser().getMy_icon()).into(holder.icon);
        holder.name.setText(orders.get(position).getMyUser().getMy_name());
        holder.time.setText(orders.get(position).getUpdatedAt());
        holder.say.setText(orders.get(position).getSay_say());
        if (orders.get(position).isHave_icon()) {
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for (int j = 0; j < orders.get(position).getSay_image().size(); j++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(orders.get(position).getSay_image().get(j));
                info.setBigImageUrl(orders.get(position).getSay_image().get(j));
                imageInfo.add(info);
            }
            holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));
        }
        else {
            holder.nineGrid.setVisibility(View.GONE);
        }
        return convertView;
    }

    public AdapterDatachange adapterDatachange;

    public void setadapterDatachange(AdapterDatachange adapterDatachange) {
        this.adapterDatachange = adapterDatachange;
    }

    public interface AdapterDatachange {
        void change();
    }

    public class ViewHolder {
        private TextView name;
        private TextView time;
        private TextView good;
        private TextView comments;
        private TextView say;
        private ImageView icon;
        private ImageView say_list_good;
        private ImageView say_list_say;
        private ImageView say_list_see;
        private EditText say_list_edi;
        private NineGridView nineGrid;
    }
}
