package net.codepig.stuffnote;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import net.codepig.stuffnote.common.BaseConfig;

import static net.codepig.stuffnote.DataPresenter.SharedPreferencesManager.SaveOrder;

/**
 * 设置页
 */
public class SettingPage extends AppCompatActivity {
    private ImageView backBtn;
    private TextView OrderText,privacyNote,myMail;
    private Switch OrderSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView(){
        backBtn=findViewById(R.id.backBtn);
        OrderSwitch=findViewById(R.id.OrderSwitch);
        OrderText=findViewById(R.id.OrderText);
        privacyNote=findViewById(R.id.privacyNote);
        myMail=findViewById(R.id.myMail);

        if(BaseConfig.OrderByFrequency){
            OrderSwitch.setChecked(true);
            OrderText.setText("按查看次数排列。");
        }else{
            OrderSwitch.setChecked(false);
            OrderText.setText("按加入时间排列。");
        }

        backBtn.setOnClickListener(btnClick);
        privacyNote.setOnClickListener(btnClick);
        myMail.setOnClickListener(btnClick);
        OrderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    OrderText.setText("按查看次数排列。");
                    SaveOrder(true);
                }else{
                    OrderText.setText("按加入时间排列。");
                    SaveOrder(false);
                }
            }
        });
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.backBtn:
                    finish();
                    break;
                case R.id.privacyNote:
                    Uri uri = Uri.parse("https://github.com/codeqian/StuffNote/blob/master/privacy.md");
                    startActivity(new Intent(Intent.ACTION_VIEW,uri));
                    break;
                case R.id.myMail:
                    Uri uri2 = Uri.parse("mailto:qzdszz@163.com");
                    startActivity(new Intent(Intent.ACTION_SENDTO,uri2));
                    break;
            }
        }
    };
}
