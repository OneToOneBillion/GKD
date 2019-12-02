package cn.edu.tongji.sse.twitch.gkd.view.initView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.view.UserLoginView.UserLoginActivity;

public class InitActivity extends AppCompatActivity implements IInitView {
    SharedPreferences accountSp, sysSettingSp;

    private String mLanguage, mFontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);

        //如果是第一次运行本应用，则会先创建两个应用会用到的SharedPreferences本地文件
        //accountSp用来存储记住的账号信息
        //sysSettingSp用来存储系统设置[系统默认设置语言为English，字体大小为Middle]
        accountSp=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        sysSettingSp=this.getSharedPreferences("sysSetting", Context.MODE_PRIVATE);
        Editor editor=sysSettingSp.edit();
        if(sysSettingSp.getString("language","").equals("")){
            editor.putString("language", "English");
        }
        if(sysSettingSp.getString("font_size","").equals("")){
            editor.putString("font_size","Middle");
        }
        editor.apply();
        Timer t=new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                toUserLoginActivity();
            }
        };
        t.schedule(task, 3000);
    }

    @Override
    public void toUserLoginActivity(){
        Intent intent = new Intent(InitActivity.this, UserLoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
