package net.codepig.stuffnote.DataPresenter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.DataBean.ToDoInfo;

import java.util.ArrayList;
import java.util.List;

import static net.codepig.stuffnote.DataBean.TipInfo.ALL_TIP;
import static net.codepig.stuffnote.DataPresenter.DataBaseExecutive.CursorQueryAllItem;
import static net.codepig.stuffnote.DataPresenter.DataBaseExecutive.QueryTheItem4TipData;
import static net.codepig.stuffnote.DataPresenter.DataBaseExecutive.QueryTheItemByNameData;
import static net.codepig.stuffnote.DataPresenter.DataBaseExecutive.QueryTipData;

/**
 * 统一存放数据bean
 * 所有的数据库操作也先从这里过
 */
public class BeanBox {
    private static List<ItemInfo> ItemList;//所有物品列表
    private static List<ItemInfo> TheItemList;//特定名称物品列表（用于搜索）
    private static List<ItemInfo> Item4TipList;//按标签物品列表
    private static List<TipInfo> FunctionTipList;//功能标签
    private static List<TipInfo> LocationTipList;//位置标签
    private static List<TipInfo> ColorTipList;//色彩标签
    private static List<ToDoInfo> ToDoList;//清单列表

    final static String TAG="BeanBox LOGCAT";

    public static List<ItemInfo> getItemList() {
        return ItemList;
    }
    public static void setItemList(List<ItemInfo> itemList) {
        ItemList = itemList;
    }

    public static List<ItemInfo> getTheItemList() {
        return TheItemList;
    }
    public static void setTheItemList(List<ItemInfo> itemList) {
        TheItemList = itemList;
    }

    public static List<ItemInfo> getItem4TipList() {
        return Item4TipList;
    }
    public static void setItem4TipList(List<ItemInfo> itemList) {
        Item4TipList = itemList;
    }

    public static List<TipInfo> getFunctionTipList() {
        return FunctionTipList;
    }
    public static void setFunctionTipList(List<TipInfo> functionTipList) {
        FunctionTipList = functionTipList;
    }

    public static List<TipInfo> getLocationTipList() {
        return LocationTipList;
    }
    public static void setLocationTipList(List<TipInfo> locationTipList) {
        LocationTipList = locationTipList;
    }

    public static List<TipInfo> getColorTipList() {
        return ColorTipList;
    }
    public static void setColorTipList(List<TipInfo> colorTipList) {
        ColorTipList = colorTipList;
    }

    public static List<ToDoInfo> getToDoList() {
        return ToDoList;
    }
    public static void setToDoList(List<ToDoInfo> toDoList) {
        ToDoList = toDoList;
    }

    public static void set_context(Context _c){
        DataBaseExecutive.set_context(_c);
    }
    public static void initSqlManager(){
        DataBaseExecutive.initSqlManager();
    }

    /**
     * 获得标签列表
     */
    public static int GetTipList(){
        List<TipInfo> _list=new ArrayList<>();
        LocationTipList=new ArrayList<>();
        FunctionTipList=new ArrayList<>();
        ColorTipList=new ArrayList<>();
        //查询
        Cursor c=QueryTipData(ALL_TIP);
        if(c==null){
            Log.d(TAG,"c==null");
            return 0;
        }else{
            int listLength = c.getCount();
            for (int i = 0; i < listLength; i++) {
                TipInfo _tip=new TipInfo();
                _tip.set_type(Integer.parseInt(c.getString(0)));
                _tip.set_value(c.getString(1));
                _list.add(_tip);
                c.moveToNext();
//                Log.d(TAG,"tip info:"+_tip.get_type()+"_"+_tip.get_value());
            }
            c.close();
        }
        //分表
        for (int i=0;i<_list.size();i++){
            TipInfo _tip=_list.get(i);
            switch (_tip.get_type()){
                case TipInfo.LOCATION_TIP:
                    LocationTipList.add(_tip);
                    break;
                case TipInfo.FUNCTION_TIP:
                    FunctionTipList.add(_tip);
                    break;
                case TipInfo.COLOR_TIP:
                    ColorTipList.add(_tip);
                    break;
            }
        }
//        Log.d(TAG,"LocationTipList:"+LocationTipList.size());
        return _list.size();
    }

    /**
     * 插入新标签
     */
    public static int InsertNewTip(ItemInfo _info){
        int _addCount=0;
        //依次加入各标签
        if(_info.get_location()!=null && !_info.get_location().equals("")) {
            long _id = DataBaseExecutive.InsertTipData(TipInfo.LOCATION_TIP,_info.get_location());
            if(_id>0){
                _addCount++;
            }
        }
        if(_info.get_function()!=null && !_info.get_function().equals("")) {
            long _id = DataBaseExecutive.InsertTipData(TipInfo.FUNCTION_TIP,_info.get_function());
            if(_id>0){
                _addCount++;
            }
        }
        if(_info.get_color()!=null && !_info.get_color().equals("")) {
            long _id = DataBaseExecutive.InsertTipData(TipInfo.COLOR_TIP,_info.get_color());
            if(_id>0){
                _addCount++;
            }
        }
        return _addCount;
    }

    /**
     * 获得所有物品列表
     */
    public static int GetItemList(){
        ItemList=new ArrayList<>();
        //查询
        Cursor c=CursorQueryAllItem();
        if(c==null){
            Log.d(TAG,"c==null");
            return 0;
        }else{
            int listLength = c.getCount();
            for (int i = 0; i < listLength; i++) {
                ItemInfo _item=new ItemInfo();
//                "_id","_name","_loc", "_fun", "_color","_des", "_image","_time"
                _item.set_id(c.getString(0));
                _item.set_name(c.getString(1));
//                Log.d(TAG,"_item:"+_item.get_name());
                _item.set_location(c.getString(2));
                _item.set_function(c.getString(3));
                _item.set_color(c.getString(4));
                _item.set_description(c.getString(5));
                _item.set_imageUrl(c.getString(6));
                _item.set_time(c.getString(7));
                _item.set_fq(c.getInt(8));
                ItemList.add(_item);
                c.moveToNext();
            }
            c.close();
        }
        return ItemList.size();
    }

    /**
     * 删除物品
     */
    public static void DeleteTheItem(ItemInfo _info){
        DataBaseExecutive.DeleteItemData(_info.get_id());
    }

    /**
     * 查询单一物品信息
     * @return
     */
    public static int GetTheItemByNameList(String _name){
        TheItemList=new ArrayList<>();
        //查询
        Cursor c=QueryTheItemByNameData(_name);
//        Log.d(TAG,"c.getCount():"+c.getCount());
        if(c==null){
            Log.d(TAG,"c==null");
            return 0;
        }else{
            int listLength = c.getCount();
            for (int i = 0; i < listLength; i++) {
                ItemInfo _item=new ItemInfo();
//                "_id","_name","_loc", "_fun", "_color","_des", "_image","_time"
                _item.set_id(c.getString(0));
                _item.set_name(c.getString(1));
//                Log.d(TAG,"_item:"+_item.get_name());
                _item.set_location(c.getString(2));
                _item.set_function(c.getString(3));
                _item.set_color(c.getString(4));
                _item.set_description(c.getString(5));
                _item.set_imageUrl(c.getString(6));
                _item.set_time(c.getString(7));
                _item.set_fq(c.getInt(8));
                TheItemList.add(_item);
                c.moveToNext();
            }
            c.close();
        }
        return TheItemList.size();
    }

    /**
     * 查询单一标签物品列表
     * @return
     */
    public static int GetItem4TipList(int _tip,String _v){
        Item4TipList=new ArrayList<>();
        //查询
        Cursor c=QueryTheItem4TipData(_tip,_v);
        if(c==null){
            Log.d(TAG,"c==null");
            return 0;
        }else{
            int listLength = c.getCount();
            for (int i = 0; i < listLength; i++) {
                ItemInfo _item=new ItemInfo();
//                "_id","_name","_loc", "_fun", "_color","_des", "_image","_time"
                _item.set_id(c.getString(0));
                _item.set_name(c.getString(1));
//                Log.d(TAG,"_item:"+_item.get_name());
                _item.set_location(c.getString(2));
                _item.set_function(c.getString(3));
                _item.set_color(c.getString(4));
                _item.set_description(c.getString(5));
                _item.set_imageUrl(c.getString(6));
                _item.set_time(c.getString(7));
                _item.set_fq(c.getInt(8));
                Item4TipList.add(_item);
                c.moveToNext();
            }
            c.close();
        }
        return Item4TipList.size();
    }

    /**
     * 插入物品信息
     * @param _info
     * @return
     */
    public static long InsertNewItem(ItemInfo _info){
        long _id=DataBaseExecutive.InsertItemData(_info.get_name(),_info.get_location(),_info.get_function(),_info.get_color(),_info.get_description(),_info.get_imageUrl(),_info.get_time());
        return _id;
    }

    /**
     * 更新物品信息
     * @param _info
     * @return
     */
    public static long UpdateItem(ItemInfo _info){
        long _id=DataBaseExecutive.UpdateItem(_info.get_id(),_info.get_name(),_info.get_location(),_info.get_function(),_info.get_color(),_info.get_description(),_info.get_imageUrl(),_info.get_time(),_info.get_fq());
        return _id;
    }
}
