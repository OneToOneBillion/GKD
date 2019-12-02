package cn.edu.tongji.sse.twitch.gkd.view.RunningView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class RunningActivity extends AppCompatActivity implements IRunningView{
    private ImageButton personal,running,create_post;
    private Chronometer chronometer;
    private ToggleButton run_stop;
    private Button stop;
    private long run_time=0;
    private ToggleButton btn_bgm;
    private ImageButton btn_last, btn_next;
    private MediaPlayer mp;
    private TextView tSports;

    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_ui);

        sysSettingSp=this.getSharedPreferences("sysSetting", Context.MODE_PRIVATE);

        personal=findViewById(R.id.personnal);
        running=findViewById(R.id.running);
        create_post=findViewById(R.id.create_post);
        chronometer=findViewById(R.id.chronometer);
        run_stop=findViewById(R.id.run_stop);
        stop=findViewById(R.id.stop);
        btn_bgm=findViewById(R.id.btn_play_pause);
        btn_last=findViewById(R.id.btn_last);
        btn_next=findViewById(R.id.btn_next);
        tSports=findViewById(R.id.sportsTextView);

        mp=MediaPlayer.create(this,R.raw.empire);

        //跳转个人界面
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningActivity.this, PersonalActivity.class);
                startActivity(intent);
                onDestroy();
            }
        });

        //跳转运动界面
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningActivity.this, RunningActivity.class);
                startActivity(intent);
                onDestroy();
            }
        });

        //跳转动态界面
        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningActivity.this, SocialActivity.class);
                startActivity(intent);
                onDestroy();
            }
        });

        //计时器
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //提醒
                if (SystemClock.elapsedRealtime()-chronometer.getBase()>5000&&SystemClock.elapsedRealtime()-chronometer.getBase()<6000){
                    Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                    vibrator.vibrate(2000);
                    Toast.makeText(getApplicationContext(),"已经运动五秒钟了，加油哦",Toast.LENGTH_LONG).show();
                }
            }
        });
        run_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //计时
                if(run_stop.isChecked()){
                    chronometer.setBase(SystemClock.elapsedRealtime()-run_time);
                    chronometer.start();
                }
                else {
                    run_time=SystemClock.elapsedRealtime()-chronometer.getBase();
                    chronometer.stop();//显示的计时会停止，但再点开始不会真正的停止，系统初始时间不会改变
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run_time=0;
                chronometer.setBase(SystemClock.elapsedRealtime());
            }
        });

        //设置音乐播放按钮响应事件
        btn_bgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(btn_bgm.isChecked()) {
                        mp.start();
                    }
                    else{
                        mp.pause();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //设置播放上一首音乐按钮响应事件
        btn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
            }
        });

        //设置播放播放下一首音乐按钮响应事件
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
            }
        });

        //语言设置
        if(sysSettingSp.getString("language","").equals("English")){
            tSports.setText(R.string.sports_en);
        }
        else{
            tSports.setText(R.string.sports_cn);
        }
    }
}


