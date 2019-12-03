package cn.edu.tongji.sse.twitch.gkd.view.BlackView;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.view.initView.InitActivity;

public class BlankActivity extends AppCompatActivity implements IBlankView {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank);

        toInitActivity();
    }

    @Override
    public void toInitActivity(){
        Intent intent = new Intent(BlankActivity.this, InitActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
