package cn.edu.tongji.sse.twitch.gkd.view.PersonalView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.view.RecyclerViewAdapter;
import cn.edu.tongji.sse.twitch.gkd.view.RunningView.RunningActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SettingView.SystemSettingActivity;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class PersonalActivity extends AppCompatActivity implements IPersonalView{
    private ImageButton personal,running,create_post,setting;
    private RecyclerView running_data,ranking_list;
    private RecyclerViewAdapter running_data_adapter,ranking_list_adapter;//声明适配器
    private Context context;
    private List<String> show_running_data_list,show_ranking_list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.personal_ui);

        personal=findViewById(R.id.personnal);
        running=findViewById(R.id.running);
        create_post=findViewById(R.id.create_post);
        setting=findViewById(R.id.setting);
        running_data=findViewById(R.id.running_data);
        ranking_list=findViewById(R.id.ranking_list);

        show_running_data_list = new ArrayList<>();
        show_running_data_list.add("运动记录：");
        for (int i=0;i<8;i++){
            show_running_data_list.add("这是第"+i+"次运动");
        }
        running_data_adapter = new RecyclerViewAdapter(context,show_running_data_list);
        LinearLayoutManager run_manager = new LinearLayoutManager(context);
        run_manager.setOrientation(LinearLayoutManager.VERTICAL);
        running_data.setLayoutManager(run_manager);
        running_data.setAdapter(running_data_adapter);

        show_ranking_list = new ArrayList<>();
        show_ranking_list.add("用户运动排行榜：");
        for (int i=0;i<8;i++){
            show_ranking_list.add("第"+i+"名");
        }
        ranking_list_adapter = new RecyclerViewAdapter(context,show_ranking_list);
        LinearLayoutManager rank_manager = new LinearLayoutManager(context);
        rank_manager.setOrientation(LinearLayoutManager.VERTICAL);
        ranking_list.setLayoutManager(rank_manager);
        ranking_list.setAdapter(ranking_list_adapter);

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, RunningActivity.class);
                startActivity(intent);
            }
        });

        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, SocialActivity.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, SystemSettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
