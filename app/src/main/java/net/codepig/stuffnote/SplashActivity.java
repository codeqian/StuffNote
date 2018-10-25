package net.codepig.stuffnote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {
    private Button enterBtn;
    private Context context;

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
    }
}
