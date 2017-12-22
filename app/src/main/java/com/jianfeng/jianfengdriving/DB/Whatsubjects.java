package com.jianfeng.jianfengdriving.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

///**
// * Created by Administrator on 2016/10/28.
// */
public class Whatsubjects {
    private DbSql dbSql = null;
    public Whatsubjects(Context context) {
        dbSql = new DbSql(context);
    }

    // 将数据库打开帮帮助类实例化，然后利用这个对象
    // 调用谷歌的api去进行增删改查
    // 增加的方法吗，返回的的是一个long值
    public long addDate(String name, String location,String subject) {
        // 增删改查每一个方法都要得到数据库，然后操作完成后一定要关闭
        // getWritableDatabase(); 执行后数据库文件才会生成
        // 数据库文件利用DDMS可以查看，在 data/data/包名/databases 目录下即可查看
        SQLiteDatabase sqLiteDatabase = dbSql.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("time", name);
        contentValues.put("score", location);
        // 返回,显示数据添加在第几行
        // 加了现在连续添加了3行数据,突然删掉第三行,然后再添加一条数据返回的是4不是3
        // 因为自增长
        long rowid = sqLiteDatabase.insert(subject, null, contentValues);
        sqLiteDatabase.close();
        return rowid;
    }


    // 删除的方法，返回值是int
    public int deleteDate(String time,String subject) {
        int deleteResult =-1;
        SQLiteDatabase sqLiteDatabase = dbSql.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(subject, null, null, null, null, null, null);
        if (cursor != null) {
// 循环遍历cursor
            while (cursor.moveToNext()) {
// 拿到每一行name 与hp的数值
//www.sctarena.com
                String time_temp ="时间："+ cursor.getString(cursor.getColumnIndex("time"));
//                int location_temp = cursor.getInt(cursor.getColumnIndex("score"));
                if (time_temp .equals(time)) {
                    deleteResult = sqLiteDatabase.delete(subject, "id=?", new String[]{String.valueOf(cursor.getInt(0))});
                    sqLiteDatabase.close();
                    return deleteResult;
                }

            }
        }
//        deleteResult = sqLiteDatabase.delete("contactinfo", "id=?", new String[]{location});
        sqLiteDatabase.close();
        return deleteResult;
    }

    /**
     * 修改的方法
     *
     //     * @param name
     * @param location
     * @return
     */
    public int updateData(int to ,int location) {
        int updateResult = -1;
        SQLiteDatabase sqLiteDatabase = dbSql.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("contactinfo1", null, null, null, null, null, null);
        if (cursor != null) {
// 循环遍历cursor
            while (cursor.moveToNext()) {
// 拿到每一行name 与hp的数值
//www.sctarena.com
                String name_temp = cursor.getString(cursor.getColumnIndex("name"));
                int location_temp = cursor.getInt(cursor.getColumnIndex("location"));
                if (location_temp == location) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("location", to);
                    updateResult = sqLiteDatabase.update("contactinfo1", contentValues, "name=?", new String[]{name_temp});
                    sqLiteDatabase.close();
                    return updateResult;
                }

            }
        }
        return updateResult;
    }

    /**
     * 查询的方法（查找位置）
     *
     * @param name
     * @return
     */
    public int alterDate(String name,String subject) {
//
//判断cursor不为空 这个很重要
        int location = -1;
        SQLiteDatabase sqLiteDatabase = dbSql.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(subject, null, null, null, null, null, null);
        if (cursor != null) {
// 循环遍历cursor
            while (cursor.moveToNext()) {
// 拿到每一行name 与hp的数值
//www.sctarena.com
                String name_temp = cursor.getString(cursor.getColumnIndex("name"));
                int location_temp = cursor.getInt(cursor.getColumnIndex("location"));
                if (name.equals(name_temp)) {
                    location = location_temp;
                    sqLiteDatabase.close();
                    cursor.close();
                    System.out.println(name_temp + "  和  " + location_temp);
                    return location;
                }

            }
// 关闭
        }
        sqLiteDatabase.close();
        cursor.close();
        return location;
    }
}

