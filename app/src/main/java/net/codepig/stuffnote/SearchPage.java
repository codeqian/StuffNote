package net.codepig.stuffnote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SearchPage extends AppCompatActivity {
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        initView();
    }

    private void initView(){
        backBtn=findViewById(R.id.backBtn);

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
