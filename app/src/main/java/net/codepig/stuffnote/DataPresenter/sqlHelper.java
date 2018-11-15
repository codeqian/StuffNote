package net.codepig.stuffnote.DataPresenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.codepig.stuffnote.common.BaseConfig;

/**
 * 管理sql数据
 * Created by QZD on 2015/3/25.
 */
public class sqlHelper extends SQLiteOpenHelper {
    public sqlHelper(Context context)
    {
        super(context, BaseConfig._DbName, null, 2);//最后个参数用以更新数据库
        //this(context, name, factory, version, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("LOGCAT","creatDb");
        //物品信息表(id，名称，位置，功能，色彩，描述，图片名称,时间戳)
        db.execSQL("CREATE TABLE " + BaseConfig._ItemListTableName +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, _name VARCHAR, _loc VARCHAR, _fun VARCHAR, _color VARCHAR, _des VARCHAR, _image VARCHAR, _time VARCHAR)");
        //标签表(id，标签类型，标签名
        db.execSQL("CREATE TABLE " + BaseConfig._TipListTableName +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, _type INTEGER, _name VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("LOGCAT","Version:"+oldVersion+" to "+newVersion);
        if(oldVersion < newVersion) {
            //删除旧表
            db.execSQL("DROP TABLE IF EXISTS " + BaseConfig._ItemListTableName);
            db.execSQL("DROP TABLE IF EXISTS " + BaseConfig._TipListTableName);
        }
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
