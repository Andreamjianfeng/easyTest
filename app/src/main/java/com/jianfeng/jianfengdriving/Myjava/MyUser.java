package com.jianfeng.jianfengdriving.Myjava;

/**
 * Created by 吴剑锋 on 2017/4/29.
 * 用户类
 */

import java.util.List;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {

    private static final long serialVersionUID = 1L;
    private Integer age;
    private Integer num;
    private Boolean sex;
   private String my_icon;
   private String my_name;

    public void setMy_name(String my_name) {
        this.my_name = my_name;
    }

    public String getMy_name() {
        return my_name;
    }

    public String getMy_icon() {

        return my_icon;
    }

    public void setMy_icon(String my_icon) {
        this.my_icon = my_icon;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
