package net.codepig.stuffnote.DataPresenter;

import android.database.Cursor;
import android.util.Log;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.DataBean.ToDoInfo;

import java.util.ArrayList;
import java.util.List;

import static net.codepig.stuffnote.DataBean.TipInfo.ALL_TIP;
import static net.codepig.stuffnote.DataBean.TipInfo.COLOR_TIP;
import static net.codepig.stuffnote.DataBean.TipInfo.FUNCTION_TIP;
import static net.codepig.stuffnote.DataBean.TipInfo.LOCATION_TIP;
import static net.codepig.stuffnote.DataPresenter.DataBaseExecutive.InsertTipData;
import static net.codepig.stuffnote.DataPresenter.DataBaseExecutive.QueryTipData;

/**
 * 统一存放数据bean
 * 所有的数据库操作也先从这里过
 */
public class BeanBox {
    private static List<ItemInfo> ItemList;
    private static List<TipInfo> FunctionTipList;
    private static List<TipInfo> LocationTipList;
    private static List<TipInfo> ColorTipList;
    private static List<ToDoInfo> ToDoList;

    final static String TAG="BeanBox LOGCAT";

    public static List<ItemInfo> getItemList() {
        return ItemList;
    }

    public static void setItemList(List<ItemInfo> itemList) {
        ItemList = itemList;
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
                Log.d(TAG,"tip info:"+_tip.get_type()+"_"+_tip.get_value());
            }
            c.close();
        }
        //分表
        for (int i=0;i<_list.size();i++){
            TipInfo _tip=_list.get(i);
            switch (_tip.get_type()){
                case 0:
                    LocationTipList.add(_tip);
                    break;
                case 1:
                    FunctionTipList.add(_tip);
                    break;
                case 2:
                    ColorTipList.add(_tip);
                    break;
            }
        }
        Log.d(TAG,"LocationTipList:"+LocationTipList.size());
        return _list.size();
    }

    /**
     * 获得物品列表
     */
    public static int GetItemList(){
        ItemList=new ArrayList<>();
        return ItemList.size();
    }

    //    测试数据---------------------------------------------------------------------------------------
    public static void testTipList(){
        InsertTipData(LOCATION_TIP,"客厅");
        InsertTipData(FUNCTION_TIP,"日用品");
        InsertTipData(COLOR_TIP,"red");
    }

    public static void testItemList(){
        ItemList=new ArrayList<>();
        for (int i=0;i<20;i++){
            ItemInfo _item=new ItemInfo();
            _item.set_name("物品"+1);
            _item.set_location("位置"+i);
            _item.set_color(0);
            _item.set_function("功能"+i);
            _item.set_description("物品描述……");
            ItemList.add(_item);
        }
    }
}
