package net.codepig.stuffnote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.codepig.stuffnote.common.BaseConfig;
import net.codepig.stuffnote.values.dimens;

public class SplashActivity extends AppCompatActivity {
    private Button enterBtn;
    private Context context;

    private String TAG="splash LOGCAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context=this;
        enterBtn=findViewById(R.id.enterBtn);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //获取屏幕大小，这个初始化必须要做，不然ScaleTextView无法正确计算出大小
        DisplayMetrics dm =getResources().getDisplayMetrics();
        dimens.curWidth = dm.widthPixels;
        dimens.curHeight = dm.heightPixels;
        double _w=dimens.curWidth;
        double _h=dimens.curHeight;
        dimens.appScale=_w/dimens.appWidth;
        dimens.appScaleH=_h/dimens.appHeight;
        Log.d(TAG,"当前屏幕大小为："+dimens.curWidth+"x"+dimens.curHeight);

        //获取外部存储地址
        BaseConfig.SdCardRoot= android.os.Environment.getExternalStorageDirectory().toString();
        if(BaseConfig.SdCardRoot==null || BaseConfig.SdCardRoot.equals("")){
            Log.d(TAG,"无外部存储器");
        }else{
            Log.d(TAG,"外部存储器地址为："+BaseConfig.SdCardRoot);
        }

    }
}
