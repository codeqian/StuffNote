package net.codepig.stuffnote.DataPresenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import net.codepig.stuffnote.common.BaseConfig;

/**
 * 管理SharedPreferences
 */
public class SharedPreferencesManager {
    private static SharedPreferences.Editor editor;
    private static SharedPreferences settings;

    private final static String TAG="SharedManager LOGCAT";

    public static void initSharedPreferences(Context _context){
        settings = _context.getSharedPreferences("BaseConfig", Context.MODE_PRIVATE);
        editor = settings.edit();
        BaseConfig.OrderByFrequency=settings.getBoolean("OrderByFrequency",true);
        Log.d(TAG,"BaseConfig.OrderByFrequency:"+BaseConfig.OrderByFrequency);
    }

    public static void SaveOrder(boolean _v){
        editor.putBoolean("OrderByFrequency",_v);
        editor.commit();
        BaseConfig.OrderByFrequency=_v;
        Log.d(TAG,"BaseConfig.OrderByFrequency:"+BaseConfig.OrderByFrequency);
    }
}
