package net.codepig.stuffnote.DataPresenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.codepig.stuffnote.common.BaseConfig;
import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.DataBean.ToDoInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 进行数据库的基本操作
 * 所有的操作都来自BeanBox类，不直接与界面Activity接触
 */
public class DataBaseExecutive {
    private static Context _context;
    //数据库对象
    private static sqlHelper _DbManager;
    private static SQLiteDatabase _mDB;

    final static String TAG="DataBaseExe LOGCAT";

    public static void initSqlManager(){
        if (_DbManager == null) {
            _DbManager = new sqlHelper(_context);
            _mDB=_DbManager.getWritableDatabase();
        }else{
            return;
        }
    }

    public static void set_context(Context _c){
        _context=_c;
    }

//    标签操作>>-------------------------------------------------------------------------------------------
    /**
     * 插入标签记录
     * @return
     */
    public static long InsertTipData(int _type,String _v){
        if(CheckTipData(_type,_v)>0){
            return 0;
        }
        ContentValues cv=new ContentValues();
        cv.put("_type", _type);
        cv.put("_name", _v);
        long _id=_mDB.insert(BaseConfig._TipListTableName, "null",cv);
        return _id;
    }

    /**
     * 删除标签记录
     */
    public static void DeleteTipData(String _type,String _v){
        String selection = "_type=? and _name=?";//条件
        String[] selectionArgs = new String[] { _type+"",_v };//条件值
        _mDB.delete(BaseConfig._TipListTableName, selection, selectionArgs);
    }

    /**
     * 查询标签记录
     */
    public static Cursor QueryTipData(int _type){
        if(_mDB==null){
            Log.d(TAG,"_mDB is null");
            return null;
        }
        String[] columns = new String[] { "_type","_name" };//需要返回的值
        String selection = "_type=?";//条件
        String[] selectionArgs = new String[] { _type+"" };//条件值
        if(_type==TipInfo.ALL_TIP){
            selection = "_type>=?";
            selectionArgs = new String[] { "0" };
        }
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;
        Cursor c = _mDB.query(BaseConfig._TipListTableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        c.moveToFirst();
        if(c.getCount()>0){
//            Log.d(TAG,"c.getCount():"+c.getCount());
            return c;
        }
        c.close();
        return null;
    }

    /**
     * 查询标签是否已存在
     */
    public static int CheckTipData(int _type,String _name){
        Log.d(TAG,"check for:"+_type+"_"+_name);
        if(_mDB==null){
            Log.d(TAG,"_mDB is null");
            return 0;
        }

        String[] columns = new String[] { "_type","_name" };//需要返回的值
        String selection = "_type=? and _name=?";//条件
        String[] selectionArgs = new String[] { _type+"",_name };//条件值
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;
        Cursor c = _mDB.query(BaseConfig._TipListTableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        c.moveToFirst();
        if(c.getCount()>0){
            Log.d(TAG,"check c.getCount():"+c.getCount());
            return c.getCount();
        }
        c.close();
        return 0;
    }

//    物品操作>>-------------------------------------------------------------------
    /**
     * 插入物品记录
     * @return
     */
    public static long InsertItemData(){
        ContentValues cv=new ContentValues();
//        cv.put("vid", vid);
//        cv.put("type", "video");
//        cv.put("vname", vname);
//        cv.put("uname", uname);
//        cv.put("imgUrl", imgUrl);
//        cv.put("vUrl", downUrl);
//        cv.put("done", 0);
        long _id=_mDB.insert(BaseConfig._ItemListTableName, "null",cv);
        return _id;
    }

    /**
     * 更新物品记录
     */
    public static int UpdataItem(String _type,String _v){
        int _count=0;
        return _count;
    }

    /**
     * 删除物品记录
     */
    public static void DeleteItemData(String _type,String _v){
        //
    }

    /**
     * 查询物品记录
     */
    public static void QueryItemData(String _type,String _v){

        //数据
        List<ItemInfo> ItemList;
        List<TipInfo> FunctionTipList;
        List<TipInfo> LocationTipList;
        List<TipInfo> ColorTipList;
        List<ToDoInfo> ToDoList;
    }
}
