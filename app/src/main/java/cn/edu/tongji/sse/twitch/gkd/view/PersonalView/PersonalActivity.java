package cn.edu.tongji.sse.twitch.gkd.view.PersonalView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter.IPersonalPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.PersonalPresenter.PersonalPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.Adapter.RecyclerViewAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.AddFriendView.AddFriendActivity;
import cn.edu.tongji.sse.twitch.gkd.view.ChangeInfoView.ChangeInfoActivity;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.RunningActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SystemSettingView.SystemSettingActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class PersonalActivity extends AppCompatActivity implements IPersonalView{
    private ImageButton personal,running,create_post,setting,addNewFriend,changeInfo;
    private RecyclerView running_data,ranking_list;
    private RecyclerViewAdapter running_data_adapter,ranking_list_adapter;//声明适配器
    private Context context;
    private List<String> show_running_data_list,show_ranking_list;
    private TextView tPerson;
    private IPersonalPresenter iPersonalPresenter;

    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.personal_ui);

        sysSettingSp=this.getSharedPreferences("sysSetting",MODE_PRIVATE);

        iPersonalPresenter=new PersonalPresenterImpl(this);
        personal=findViewById(R.id.personnal);
        running=findViewById(R.id.running);
        create_post=findViewById(R.id.create_post);
        setting=findViewById(R.id.setting);
        addNewFriend=findViewById(R.id.addNewFriend);
        changeInfo=findViewById(R.id.changeInfo);
        running_data=findViewById(R.id.running_data);
        ranking_list=findViewById(R.id.ranking_list);
        tPerson=findViewById(R.id.personText);

        show_running_data_list = new ArrayList<>();
        show_running_data_list.add("运动记录：");
        iPersonalPresenter.showRunningData(getUserID(),show_running_data_list,running_data,context);

        show_ranking_list = new ArrayList<>();
        show_ranking_list.add("用户运动排行榜：");
        iPersonalPresenter.showRanking(getUserID(),show_ranking_list,ranking_list,context);

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

        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, RunningActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, SocialActivity.class);
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

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, SystemSettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
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

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }
}
