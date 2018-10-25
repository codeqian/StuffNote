package net.codepig.stuffnote.View.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import net.codepig.stuffnote.R;

import static net.codepig.stuffnote.values.dimens.appWidth;
import static net.codepig.stuffnote.values.dimens.curWidth;

/**
 * Created by QZD on 2017/11/30.
 */

public class ScaleTextView extends AppCompatTextView {
    private int baseScreenHeight = 720;
    public ScaleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray type = context.obtainStyledAttributes(attrs,R.styleable.ScaleTextView);//获得属性值
        int i = type.getInteger(R.styleable.ScaleTextView_textSizePx, 25);
//        Log.d("LOGCAT","i:"+i);
        baseScreenHeight = type.getInteger(R.styleable.ScaleTextView_baseScreenHeight, appWidth);
//        Log.d("LOGCAT","baseScreenHeight:"+baseScreenHeight);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getFontSize(i));
        boolean _isBold=type.getBoolean(R.styleable.ScaleTextView_textBold, false);
        getPaint().setFakeBoldText(_isBold);
    }

    /**
     * 获取当前手机屏幕分辨率，然后根据和设计图的比例对照换算实际字体大小
     * @param textSize
     * @return
     */
    private int getFontSize(int textSize) {
        int rate = (int) (textSize * (float) curWidth / baseScreenHeight);
        return rate;
    }
}