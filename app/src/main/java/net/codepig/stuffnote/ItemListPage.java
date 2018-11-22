package net.codepig.stuffnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.codepig.stuffnote.common.MessageCode;

/**
 * 分维度物品列表
 */
public class ItemListPage extends AppCompatActivity {
    private ImageView backBtn;
    private FrameLayout itemInfoView;
    private TextView tipTitle;

    private int _type;
    private String _key;

    private final String TAG="ItemList LOGCAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);
        Intent intent=getIntent();
        try {
            _type = Integer.parseInt(intent.getStringExtra("type"));
            _key = intent.getStringExtra("value");
        }catch (Exception e){
            finish();
        }
        Log.d(TAG,_type+"_"+_key);
        initView();
    }

    private void initView(){
        backBtn=findViewById(R.id.backBtn);
        itemInfoView=findViewById(R.id.itemInfoView);
        tipTitle=findViewById(R.id.tipTitle);
        if(_type==MessageCode.GO_COLOR){
            switch (Integer.parseInt(_key)){
                case MessageCode.RED_TIP:
                    tipTitle.setText("红");
                    break;
                case MessageCode.ORANGE_TIP:
                    tipTitle.setText("橙");
                    break;
                case MessageCode.YELLOW_TIP:
                    tipTitle.setText("黄");
                    break;
                case MessageCode.GREEN_TIP:
                    tipTitle.setText("绿");
                    break;
                case MessageCode.CYAN_TIP:
                    tipTitle.setText("靛");
                    break;
                case MessageCode.BLUE_TIP:
                    tipTitle.setText("蓝");
                    break;
                case MessageCode.PURPLE_TIP:
                    tipTitle.setText("紫");
                    break;
            }
        }else {
            tipTitle.setText(_key);
        }

        backBtn.setOnClickListener(btnClick);
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.backBtn:
                    finish();
                    break;
            }
        }
    };
}
