package cn.edu.tongji.sse.twitch.gkd.view.RunningView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_ui);

        personal=findViewById(R.id.personnal);
        running=findViewById(R.id.running);
        create_post=findViewById(R.id.create_post);
        chronometer=findViewById(R.id.chronometer);
        run_stop=findViewById(R.id.run_stop);
        stop=findViewById(R.id.stop);

        //跳转个人界面
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        //跳转运动界面
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningActivity.this, RunningActivity.class);
                startActivity(intent);
            }
        });

        //跳转动态界面
        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningActivity.this, SocialActivity.class);
                startActivity(intent);
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
    }
}


