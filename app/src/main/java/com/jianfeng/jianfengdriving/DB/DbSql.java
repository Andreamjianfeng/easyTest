package com.jianfeng.jianfengdriving.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

///**
// * Created by Administrator on 2016/10/28.
// */
public class DbSql extends SQLiteOpenHelper {
    public DbSql(Context context) {
        super(context, "launcher", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table subjects1 (id integer primary key autoincrement, time varchar(50),score varchar(10))");
        db.execSQL("create table subjects4 (id integer primary key autoincrement, time varchar(50),score varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
