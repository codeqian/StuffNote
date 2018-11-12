package net.codepig.stuffnote.DataPresenter;

import net.codepig.stuffnote.DataBean.ItemInfo;
import net.codepig.stuffnote.DataBean.TipInfo;
import net.codepig.stuffnote.DataBean.ToDoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一存放数据bean
 */
public class BeanBox {
    private static List<ItemInfo> ItemList;
    private static List<TipInfo> FunctionTipList;
    private static List<TipInfo> LocationTipList;
    private static List<TipInfo> ColorTipList;
    private static List<ToDoInfo> ToDoList;

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

    public static void testTipList(){
        LocationTipList=new ArrayList<>();
        for (int i=0;i<20;i++){
            TipInfo _tip=new TipInfo();
            _tip.set_type(TipInfo.LOCATION_TIP);
            _tip.set_value("位置"+i);
            LocationTipList.add(_tip);
        }
    }
}
