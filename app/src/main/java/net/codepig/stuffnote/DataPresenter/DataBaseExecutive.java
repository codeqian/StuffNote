package net.codepig.stuffnote.DataPresenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.codepig.stuffnote.common.BaseConfig;
import net.codepig.stuffnote.DataBean.TipInfo;


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
    private static int CheckTipData(int _type,String _name){
//        Log.d(TAG,"check for:"+_type+"_"+_name);
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
//            Log.d(TAG,"check c.getCount():"+c.getCount());
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
    public static long InsertItemData(String _name,String _loc,String _fun,String _color,String _des,String _image,String _time){
//        Log.d(TAG,"info:"+_name+"_"+_loc+"_"+_fun+"_"+_color+"_"+_des);
        ContentValues cv=new ContentValues();
        cv.put("_name", _name);
        cv.put("_loc", _loc);
        cv.put("_fun", _fun);
        cv.put("_color", _color);
        cv.put("_des", _des);
        cv.put("_image", _image);
        cv.put("_time", _time);
        long _id=_mDB.insert(BaseConfig._ItemListTableName, "null",cv);
        return _id;
    }

    /**
     * 更新物品记录
     */
    public static int UpdataItem(String _id,String _name,String _loc,String _fun,String _color,String _des,String _image,String _time){
        String selection = "_id=?";//条件
        String[] selectionArgs = new String[] { _id };//条件值
        ContentValues cv=new ContentValues();
        cv.put("_name", _name);
        cv.put("_loc", _loc);
        cv.put("_fun", _fun);
        cv.put("_color", _color);
        cv.put("_des", _des);
        cv.put("_image", _image);
        cv.put("_time", _time);
        int _updateId=_mDB.update(BaseConfig._ItemListTableName, cv, selection, selectionArgs);
        return _updateId;
    }

    /**
     * 删除物品记录
     */
    public static void DeleteItemData(String _id){
        String selection = "_id=?";//条件
        String[] selectionArgs = new String[] { _id };//条件值
        _mDB.delete(BaseConfig._ItemListTableName, selection, selectionArgs);
    }

    /**
     * 查询所有物品记录
     */
    public static Cursor CursorQueryAllItem(){
        if(_mDB==null){
            Log.d(TAG,"_mDB is null");
            return null;
        }
        String rawQuerySql =  "select * from "+BaseConfig._ItemListTableName;
        Cursor c = _mDB.rawQuery(rawQuerySql,null);
        c.moveToFirst();
//        Log.d(TAG,"item c.getCount():"+c.getCount());
        if(c.getCount()>0){
            return c;
        }
        c.close();
        return null;
    }

    /**
     * 查询特定名称物品记录
     */
    public static Cursor QueryTheItemData(String _name){
        //数据
        if(_mDB==null){
            Log.d(TAG,"_mDB is null");
            return null;
        }
        String[] columns = new String[] { "_id","_name","_loc", "_fun", "_color","_des", "_image","_time" };//需要返回的值
        String selection = "_name=?";//条件
        String[] selectionArgs = new String[] { _name };//条件值
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;
        Cursor c = _mDB.query(BaseConfig._ItemListTableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        c.moveToFirst();
        if(c.getCount()>0){
            return c;
        }
        c.close();
        return null;
    }
}
