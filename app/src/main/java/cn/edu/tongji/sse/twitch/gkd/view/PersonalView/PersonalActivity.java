package cn.edu.tongji.sse.twitch.gkd.view.PersonalView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter.IPersonalPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter.PersonalPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RecyclerViewAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.AddFriendView.AddFriendActivity;
import cn.edu.tongji.sse.twitch.gkd.view.ChangeInfoView.ChangeInfoActivity;
import cn.edu.tongji.sse.twitch.gkd.view.FollowListView.FollowListActivity;
import cn.edu.tongji.sse.twitch.gkd.view.FollowedListView.FollowedListActivity;
import cn.edu.tongji.sse.twitch.gkd.view.RankingListView.RankingListActivity;
import cn.edu.tongji.sse.twitch.gkd.view.RunningDataListView.RunningDataListActivity;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.RunningActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SystemSettingView.SystemSettingActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class PersonalActivity extends AppCompatActivity implements IPersonalView{
    private ImageButton running,create_post,personal,setting,addNewFriend,changeInfo,running_data_detail,ranking_detail;
    private RecyclerView running_data,ranking_list;
    private Context context;
    private TextView tPerson,follow_num,followed_num,post_num,punchin_num,target,neckname;
    private TextView follow,followed;
    private ImageView avater;
    private IPersonalPresenter iPersonalPresenter;
    private Bitmap bitmap;

    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.personal_ui);

        sysSettingSp=this.getSharedPreferences("sysSetting",MODE_PRIVATE);

        iPersonalPresenter=new PersonalPresenterImpl(this);
        running=findViewById(R.id.running);
        create_post=findViewById(R.id.create_post);
        personal=findViewById(R.id.personnal);
        setting=findViewById(R.id.setting);
        addNewFriend=findViewById(R.id.addNewFriend);
        changeInfo=findViewById(R.id.changeInfo);
        running_data=findViewById(R.id.running_data);
        ranking_list=findViewById(R.id.ranking_list);
        tPerson=findViewById(R.id.personText);
        running_data_detail=findViewById(R.id.running_data_detail);
        ranking_detail=findViewById(R.id.ranking_detail);
        follow=findViewById(R.id.follow);
        followed=findViewById(R.id.followed);
        follow_num=findViewById(R.id.follow_num);
        followed_num=findViewById(R.id.followed_num);
        post_num=findViewById(R.id.post_num);
        punchin_num=findViewById(R.id.punchin_num);
        neckname=findViewById(R.id.neckname);
        target=findViewById(R.id.target);
        avater=findViewById(R.id.avatar);

        initview();

        //跳转关注列表
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, FollowListActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转粉丝列表
        followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, FollowedListActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转运动界面
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, RunningActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转动态界面
        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, SocialActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转个人界面
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, PersonalActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转添加好友界面
        addNewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, AddFriendActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转修改个人信息界面
        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, ChangeInfoActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转设置界面
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, SystemSettingActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
                finish();
            }
        });

        //跳转运动数据详细界面
        running_data_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, RunningDataListActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //跳转运动排行榜详细界面
        ranking_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, RankingListActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //语言设置
        if(sysSettingSp.getString("language","").equals("English")) {
            tPerson.setText(R.string.person_en);
        }
        else{
            tPerson.setText(R.string.person_cn);
        }

    }

    private void initview(){
        //展示运动数据据
        iPersonalPresenter.showRunningData(getUserID(),running_data,context);

        //展示运动排行榜
        iPersonalPresenter.showRanking(getUserID(),ranking_list,context);

        //展示相关个人信息，关注，粉丝，动态，打卡量,头像，昵称，运动目标
        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username",getUserID());
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    follow_num.setText(list.get(0).getFollow_num()+"");
                    followed_num.setText(list.get(0).getFollowed_num()+"");
                    post_num.setText(list.get(0).getPost_num()+"");
                    punchin_num.setText(list.get(0).getPunchin_num()+"");
                    neckname.setText(getUserID());
                    target.setText(list.get(0).getTarget());

                    if(list.get(0).getAvater().equals(" ")){
                        avater.setImageResource(R.drawable.initavater);
                    }
                    else {
                        byte [] input = Base64.decode(list.get(0).getAvater(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(input, 0, input.length);
                        avater.setImageBitmap(bitmap);
                    }
                }
            }
        });
    }

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }

    public void setTarget(String target_text){
        target.setText(target_text);
    }
    public void setAvater(String avater_resource){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(avater_resource);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        avater.setImageBitmap(bitmap);
    }
}
