package com.jianfeng.jianfengdriving.Myjava;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
/**
 * Created by 吴剑锋 on 2017/5/5.
 * 空间说说数据类
 */

public class FriendSay extends BmobObject{
   private String say_icon;
   private String say_name;
   private String say_time;
   private String say_say;
    private List<String> say_image;
    private List<String> say_praises=new ArrayList<>();
    private List<String> say_comments=new ArrayList<>();
    private MyUser myUser;
    private boolean have_icon;

    public void setHave_icon(boolean have_icon) {
        this.have_icon = have_icon;
    }

    public boolean isHave_icon() {

        return have_icon;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public MyUser getMyUser() {

        return myUser;
    }

    public void setSay_icon(String say_icon) {
        this.say_icon = say_icon;
    }

    public void setSay_name(String say_name) {
        this.say_name = say_name;
    }

    public void setSay_time(String say_time) {
        this.say_time = say_time;
    }

    public void setSay_say(String say_say) {
        this.say_say = say_say;
    }

    public void setSay_image(List<String> say_image) {
        this.say_image = say_image;
    }

    public void setSay_praises(List<String> say_praises) {
        this.say_praises = say_praises;
    }

    public void setSay_comments(List<String> say_comments) {
        this.say_comments = say_comments;
    }

    public String getSay_icon() {

        return say_icon;
    }

    public String getSay_name() {
        return say_name;
    }

    public String getSay_time() {
        return say_time;
    }

    public String getSay_say() {
        return say_say;
    }

    public List<String> getSay_image() {
        return say_image;
    }

    public List<String> getSay_praises() {
        return say_praises;
    }

    public List<String> getSay_comments() {
        return say_comments;
    }
}
