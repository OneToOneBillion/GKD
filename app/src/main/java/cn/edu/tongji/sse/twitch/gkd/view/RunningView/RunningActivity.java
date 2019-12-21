package cn.edu.tongji.sse.twitch.gkd.view.RunningView;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;

import cn.edu.tongji.sse.twitch.gkd.R;

import cn.edu.tongji.sse.twitch.gkd.presenter.RunningPresenter.IRunningPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.RunningPresenter.RunningPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.presenter.TrackPresenter.TrackPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class RunningActivity extends AppCompatActivity implements IRunningView{
    private TextView tSport;
    private ImageButton personal,create_post;
    private Chronometer chronometer;
    private ToggleButton run_stop, play_pause;
    private Button stop, last, next;
    private Spinner sMusic;
    private long run_time=0;
    private int musicNo=0;

    private IRunningPresenter iRunningPresenter;
    private TrackPresenterImpl mTrackPresenterImpl;

    private MediaPlayer mp;
    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_ui);

        sysSettingSp=this.getSharedPreferences("sysSetting",MODE_PRIVATE);

        tSport=findViewById(R.id.sportText);
        iRunningPresenter=new RunningPresenterImpl(this);
        personal=findViewById(R.id.personnal);
        create_post=findViewById(R.id.create_post);
        chronometer=findViewById(R.id.chronometer);
        run_stop=findViewById(R.id.run_stop);
        play_pause=findViewById(R.id.play_pause);
        stop=findViewById(R.id.stop);
        last=findViewById(R.id.last);
        next=findViewById(R.id.next);
        sMusic=findViewById(R.id.music_spinner);
        mTrackPresenterImpl=new TrackPresenterImpl(this);

        mTrackPresenterImpl.startService();

        Resources res = getResources();
        String[] music = res.getStringArray(R.array.music);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, music);
        sMusic.setAdapter(adapter);
        sMusic.setSelection(musicNo, true);
        mp=new MediaPlayer();
        mp.reset();
        mp=MediaPlayer.create(this,R.raw.afterhours);

        //跳转个人界面
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningActivity.this, PersonalActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转动态界面
        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningActivity.this, SocialActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //设置音乐播放/暂停按钮响应事件
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(play_pause.isChecked()){
                    mp.start();
                }
                else{
                    mp.pause();
                }
            }
        });
        //设置音乐下拉框响应事件
        sMusic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mn = sMusic.getItemAtPosition(position).toString();
                if(mn.equals("afterhours.mp3")){
                    musicNo=0;
                }
                if(mn.equals("fire.mp3")){
                    musicNo=1;
                }
                if(mn.equals("Iridescent.mp3")){
                    musicNo=2;
                }
                if(mn.equals("Look At Me Now.mp3")){
                    musicNo=3;
                }
                if(mn.equals("Masked Heroes.mp3")){
                    musicNo=4;
                }
                if(mn.equals("Way Back.mp3")){
                    musicNo=5;
                }
                changeMusic(musicNo);
                mp.start();
                play_pause.setChecked(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mp.start();
            }
        });
        //设置上一首按钮响应事件
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastMusic();
            }
        });
        //设置下一首按钮响应事件
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMusic();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextMusic();
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
                    mTrackPresenterImpl.startRun();
                }
                else {
                    run_time=SystemClock.elapsedRealtime()-chronometer.getBase();
                    chronometer.stop();//显示的计时会停止，但再点开始不会真正的停止，系统初始时间不会改变
                    mTrackPresenterImpl.pauseRun();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long total_run_time=run_time;
                run_time=0;
                chronometer.setBase(SystemClock.elapsedRealtime());

                mTrackPresenterImpl.stopRun();

                Intent intent = new Intent(RunningActivity.this, RunningResultActivity.class);
                intent.putExtra("trackId",mTrackPresenterImpl.getTrackId());
                intent.putExtra("run_time",total_run_time);
                intent.putExtra("userID",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //语言设置
        if(sysSettingSp.getString("language","").equals("English")){
            tSport.setText(R.string.sports_en);
            run_stop.setTextOff("Start");
            run_stop.setTextOn("Stop");
            stop.setText(R.string.stop_en);

        }
        else{
            tSport.setText(R.string.sports_cn);
            run_stop.setTextOff("开始");
            run_stop.setTextOn("暂停");
            stop.setText(R.string.stop_cn);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mp.isPlaying()){
            mp.stop();
        }
        mp.release();
    }

    @Override
    public Context getMyContext(){
        return this.getApplicationContext();
    }

    @Override
    public Notification getMyNotification(){
        return createNotification();
    }

    public void changeMusic(int mn){
        if(mp.isPlaying())
            mp.stop();
        mp.reset();
        switch (mn){
            case 0: mp=MediaPlayer.create(this,R.raw.afterhours);break;
            case 1: mp=MediaPlayer.create(this,R.raw.fire);break;
            case 2: mp=MediaPlayer.create(this,R.raw.iridescent);break;
            case 3: mp=MediaPlayer.create(this,R.raw.look_at_me_now);break;
            case 4: mp=MediaPlayer.create(this,R.raw.masked_heroes);break;
            case 5: mp=MediaPlayer.create(this,R.raw.way_back);break;
        }
    }

    public void nextMusic(){
        if(musicNo<5)
            musicNo++;
        else
            musicNo=0;
        sMusic.setSelection(musicNo,true);
        changeMusic(musicNo);
        mp.start();
    }
    public void lastMusic(){
        if(musicNo>0)
            musicNo--;
        else
            musicNo=5;
        sMusic.setSelection(musicNo, true);
        changeMusic(musicNo);
        mp.start();
    }

    /**
     * 在8.0以上手机，如果app切到后台，系统会限制定位相关接口调用频率
     * 可以在启动轨迹上报服务时提供一个通知，这样Service启动时会使用该通知成为前台Service，可以避免此限制
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification createNotification() {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID_SERVICE_RUNNING", "app service", NotificationManager.IMPORTANCE_LOW);
            nm.createNotificationChannel(channel);
            builder = new Notification.Builder(getApplicationContext(), "CHANNEL_ID_SERVICE_RUNNING");
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        Intent nfIntent = new Intent(RunningActivity.this, RunningActivity.class);
        nfIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        builder.setContentIntent(PendingIntent.getActivity(RunningActivity.this, 0, nfIntent, 0))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("猎鹰sdk运行中")
                .setContentText("猎鹰sdk运行中");
        Notification notification = builder.build();
        return notification;
    }

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }
}


