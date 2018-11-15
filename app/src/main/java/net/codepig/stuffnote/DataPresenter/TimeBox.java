package net.codepig.stuffnote.DataPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间相关操作
 */
public class TimeBox {
    /**
     * 获取当前时间戳
     */
    public static String getCurrentTime(){
        Date date = new Date();
        String _time=date.getTime()+"";
        return _time;
    }

    /**
     * 将毫秒转换为标准日期格式
     * @param _ms
     * @return
     */
    public static String ms2Date(long _ms){
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    public static String ms2DateOnlyDay(long _ms){
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }

    public static String ms2DateOnlyDay2(long _ms){
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return format.format(date);
    }

    /**
     * 标准时间转换为时间戳
     * @param _data
     * @return
     */
    public static long Date2ms(String _data){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(_data);
            return date.getTime();
        }catch(Exception e){
            return 0;
        }
    }
}
